package team.gsmgogo.global.config;

import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/team").hasAuthority("LEADER")
                        .requestMatchers(HttpMethod.POST, "/team/normal").hasAuthority("LEADER")
                        .requestMatchers(HttpMethod.DELETE, "/team").hasAuthority("LEADER")
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(new TokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
