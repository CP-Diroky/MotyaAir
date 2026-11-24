package motyaair.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlightDto {
    @NotBlank
    private String flightNumber;
    @Valid
    private AirportDto departureAirport;
    @Valid
    private AirportDto arrivalAirport;
    @Valid
    private AircraftDto aircraft;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime arrivalTime;
}
