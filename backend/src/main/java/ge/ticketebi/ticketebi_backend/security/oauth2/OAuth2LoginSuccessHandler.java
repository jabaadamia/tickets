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

        String redirectUrl = "http://localhost:3000/auth/callback"
                + "?token=" + jwt;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        clearAuthenticationAttributes(request);
    }
}