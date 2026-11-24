package motyaair.controllers.admin_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.AircraftDto;
import motyaair.dto.SeatDto;
import motyaair.mappers.RowMappers;
import motyaair.services.AircraftService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/aircrafts")
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @PostMapping
    public AircraftDto addAircraft(@RequestBody @Valid AircraftDto aircraftDto) {
        return RowMappers.aircraftDto(aircraftService.addAircraft(RowMappers.aircraft(aircraftDto)));
    }

    @GetMapping("/{aircraftId}")
    public AircraftDto getAircraft(@PathVariable @Positive Long aircraftId) {
        return RowMappers.aircraftDto(aircraftService.getAircraft(aircraftId));
    }

    @PatchMapping("/{aircraftId}/seats")
    public void addSeatsToAircraft(@RequestBody List<SeatDto> seatsDtoList, @PathVariable @Positive Long aircraftId) {
        aircraftService.addSeatsToAircraft(RowMappers.seatList(seatsDtoList), aircraftId);
    }

    @DeleteMapping("/{aircraftId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAircraft(@PathVariable @Positive Long aircraftId) {
        aircraftService.deleteAircraft(aircraftId);
    }
}
