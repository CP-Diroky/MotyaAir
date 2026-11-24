package motyaair.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import motyaair.dto.UserDto.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {
    @Nullable
    private FlightSeatDto flightSeatDto;
    @Valid
    private UserDto userDto;
    @NotNull
    private LocalDateTime expirationDate;
    private boolean isPaid;
}
