package team.gsmgogo.global.security.jwt.filter;

import team.gsmgogo.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/auth/refresh");
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String token = jwtProvider.resolveAccessToken(request);

        if (token != null) {
            Authentication authentication = jwtProvider.authorization(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

}