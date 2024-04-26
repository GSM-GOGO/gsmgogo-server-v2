package team.gsmgogo.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team.gsmgogo.global.exception.TokenException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch(TokenException e){
            httpServletResponse.setStatus(e.getStatusCode().value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");
            ErrorObject errorObject = new ErrorObject(e.getMessage());

            objectMapper.writeValue(httpServletResponse.getWriter(), errorObject);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
