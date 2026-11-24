package motyaair.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import motyaair.models.SeatClass;

@Getter
@Setter
@NoArgsConstructor
public class SeatDto {
    @NotBlank
    private String seatNumber;
    @Valid
    private AircraftDto aircraft;
    @NotNull
    private SeatClass seatClass;
}
