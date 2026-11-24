package motyaair.services;

import motyaair.models.Aircraft;
import motyaair.models.Seat;

import java.util.List;

public interface AircraftService {

    Aircraft addAircraft(Aircraft aircraft);

    Aircraft getAircraft(Long aircraftId);

    void addSeatsToAircraft(List<Seat> seats, Long aircraftId);

    void deleteAircraft(Long aircraftId);
}
