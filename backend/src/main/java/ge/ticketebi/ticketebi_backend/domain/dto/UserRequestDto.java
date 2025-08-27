package ge.ticketebi.ticketebi_backend.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "username must not be empty")
    private String username;

    @Email
    @NotBlank(message = "email must not be empty")
    private String email;

    private String phoneNumber;

    @Size(min = 8)
    private String password;   // only used for registration

}