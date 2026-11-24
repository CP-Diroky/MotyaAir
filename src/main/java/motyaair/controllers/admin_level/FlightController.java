package motyaair.controllers.admin_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.FlightDto;
import motyaair.dto.FlightSeatDto;
import motyaair.mappers.RowMappers;
import motyaair.services.FlightService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/flights")
@Validated
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public FlightDto addFlight(@RequestBody @Valid FlightDto flightDto) {
        return RowMappers.flightDto(flightService.addFlight(RowMappers.flight(flightDto)));
    }

    @GetMapping
    public List<FlightDto> getFlights() {
        return RowMappers.flightDtoList(flightService.getFlights());
    }

    @GetMapping("/{flightId}")
    public FlightDto getFlight(@PathVariable @Positive Long flightId) {
        return RowMappers.flightDto(flightService.getFlight(flightId));
    }


    @PostMapping("/{flightId}/seats")
    public void addFlightSeats(@RequestBody List<FlightSeatDto> flightSeats,
                               @PathVariable @Positive Long flightId) {
        flightService.addFlightSeats(RowMappers.flightSeatList(flightSeats), flightId);
    }

    @GetMapping("/{flightId}/seats")
    public List<FlightSeatDto> getFlightSeats(@PathVariable @Positive Long flightId) {
        return RowMappers.flightSeatDtoList(flightService.getFlightSeats(flightId));
    }
}
