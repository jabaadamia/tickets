package ge.ticketebi.ticketebi_backend.security.oauth2;

import ge.ticketebi.ticketebi_backend.domain.entities.User;
import ge.ticketebi.ticketebi_backend.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        CustomOidcUserPrincipal principal = (CustomOidcUserPrincipal) authentication.getPrincipal();

        User user = principal.getUser();

        String jwt = jwtService.generateAccessToken(user);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("""
            {
              "token":"%s",
              "tokenType":"Bearer",
              "username":"%s",
              "role":"%s"
            }
            """.formatted(jwt, user.getUsername(), user.getRole().name()));
        response.getWriter().flush();
        clearAuthenticationAttributes(request);
    }
}