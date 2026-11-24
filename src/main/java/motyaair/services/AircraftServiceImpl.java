package motyaair.services;


import motyaair.exceptions.NotFoundException;
import motyaair.models.Aircraft;
import motyaair.models.Seat;
import motyaair.repositories.AircraftRepository;
import motyaair.repositories.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final SeatRepository seatRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository, SeatRepository seatRepository) {
        this.aircraftRepository = aircraftRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public Aircraft addAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @Override
    public Aircraft getAircraft(Long aircraftId) {
        return aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new NotFoundException("Самолёт с id = " + aircraftId + " не найден!"));
    }

    @Override
    @Transactional
    public void addSeatsToAircraft(List<Seat> seats, Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new NotFoundException("Самолёт с id = " + aircraftId + " не найден!"));
        if (seats == null || seats.isEmpty()) return;
        seats.forEach(seat -> seat.setAircraft(aircraft));
        seatRepository.saveAll(seats);
    }

    @Override
    @Transactional
    public void deleteAircraft(Long aircraftId) {
        aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new NotFoundException("Самолёт с id = " + aircraftId + " не найден!"));
        aircraftRepository.deleteById(aircraftId);
    }


}


