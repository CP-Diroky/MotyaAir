package motyaair.services;


import motyaair.exceptions.NotFoundException;
import motyaair.models.Airport;
import motyaair.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional
    public Airport addAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    @Transactional
    public void deleteAirport(Long airportId) {
        airportRepository.findById(airportId)
                .orElseThrow(() -> new NotFoundException("Аэропорт с id = " + airportId + " не найден!"));
        airportRepository.deleteById(airportId);
    }
}
