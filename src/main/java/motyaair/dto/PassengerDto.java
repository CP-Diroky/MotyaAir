package motyaair.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PassengerDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
