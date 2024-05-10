package example.test.user.management.dto;

import example.test.user.management.lib.ValidBirthDate;
import example.test.user.management.lib.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    @ValidEmail
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @ValidBirthDate
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
