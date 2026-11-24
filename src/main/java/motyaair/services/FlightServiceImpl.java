package motyaair.services;


import motyaair.exceptions.NotFoundException;
import motyaair.models.Flight;
import motyaair.models.FlightSeat;
import motyaair.models.FlightSeatStatus;
import motyaair.repositories.FlightRepository;
import motyaair.repositories.FlightSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;

    public FlightServiceImpl(FlightRepository flightRepository, FlightSeatRepository flightSeatRepository) {
        this.flightRepository = flightRepository;
        this.flightSeatRepository = flightSeatRepository;
    }

    @Override
    @Transactional
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlight(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Рейс с id = " + flightId + " не найден!"));
    }



    @Override
    @Transactional
    public void addFlightSeats(List<FlightSeat> flightSeats, Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Рейс с id = " + flightId + " не найден!"));
        flightSeats.forEach(flightSeat -> {
            flightSeat.setFlight(flight);
            flightSeat.setStatus(FlightSeatStatus.AVAILABLE);
        });
        flightSeatRepository.saveAll(flightSeats);
    }

    @Override
    public List<FlightSeat> getFlightSeats(Long flightId) {
        flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Рейс с id = " + flightId + " не найден!"));
        return flightSeatRepository.findByFlightId(flightId);
    }

}
