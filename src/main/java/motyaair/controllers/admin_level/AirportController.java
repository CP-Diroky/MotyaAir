package motyaair.controllers.admin_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.AirportDto;
import motyaair.mappers.RowMappers;
import motyaair.services.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/airports")
@Validated
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    public AirportDto addAirport(@RequestBody @Valid AirportDto airportDto) {
        return RowMappers.airportDto(airportService.addAirport(RowMappers.airport(airportDto)));
    }

    @DeleteMapping("/{airportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAirport(@PathVariable @Positive Long airportId) {
        airportService.deleteAirport(airportId);
    }



}
