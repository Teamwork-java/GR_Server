package md.gr_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Username can't be empty")
    @Pattern(regexp = "[-_a-zA-Z0-9]{4,50}", message = "Provide valid username")
    private String username;

    @Email(message = "Provide valid email")
    @Size(min = 6, max = 254, message = "Email too long")
    private String email;

    @NotBlank(message = "Please provide a password")
    @Size(min = 8, max = 50, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must have at least 1 number, 1 letter Uppercase and Lowercase and 1 special character"
    )
    private String password;
}
