package ge.ticketebi.ticketebi_backend.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
