package motyaair.dto.UserDto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    @Email
    private String email;

}
