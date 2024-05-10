package example.test.user.management.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserUpdatedFieldDto {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
