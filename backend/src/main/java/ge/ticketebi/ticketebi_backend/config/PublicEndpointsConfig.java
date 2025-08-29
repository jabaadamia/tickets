package ge.ticketebi.ticketebi_backend.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicEndpointsConfig {
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",
            "/oauth2/**",
            "/login/oauth2/**",
            "/health",
            "/api/docs/**",
            "/api/swagger/**",
            "/api/swagger-ui/**"
    };
}
