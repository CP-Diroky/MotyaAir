package motyaair.controllers.user_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.TicketDto;
import motyaair.mappers.RowMappers;
import motyaair.services.TicketService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public TicketDto createTicket(@RequestBody @Valid TicketDto ticketDto,
                                  @RequestParam @Positive Long bookingId, @RequestParam @Positive Long passengerId) {
        return RowMappers.ticketDto(ticketService.createTicket(RowMappers.ticket(ticketDto), bookingId, passengerId));
    }

    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@RequestParam @Positive Long ticketId) {
        return RowMappers.ticketDto(ticketService.getTicket(ticketId));
    }
}
