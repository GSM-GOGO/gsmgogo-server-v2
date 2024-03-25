package team.gsmgogo.global.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import team.gsmgogo.global.security.jwt.TokenProvider;
import team.gsmgogo.global.security.jwt.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
               .requestMatchers(toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/team").hasAuthority("LEADER")
                        .requestMatchers(HttpMethod.POST, "/team/nomal").hasAuthority("LEADER")
                        .requestMatchers(HttpMethod.DELETE, "/team").hasAuthority("LEADER")
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(new TokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
