package motyaair.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketDto {
    @Valid
    private PassengerDto passenger;
    @NotBlank
    private String ticketNumber;
    @Valid
    private BookingDto booking;
}
