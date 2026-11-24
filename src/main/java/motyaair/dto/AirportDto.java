package motyaair.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AirportDto {
    @NotBlank
    private String airportName;
    @NotBlank
    @Size(min = 3, max = 3)
    private String code;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
}
