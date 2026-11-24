package motyaair.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import motyaair.models.FlightSeatStatus;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FlightSeatDto {
    @Valid
    private FlightDto flight;
    @Valid
    private SeatDto seat;
    @NotNull
    private BigDecimal price;
    @NotNull
    private FlightSeatStatus status;
}
