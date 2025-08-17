package ge.ticketebi.ticketebi_backend.domain.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String email;
    private String password;
    private String username;
}
