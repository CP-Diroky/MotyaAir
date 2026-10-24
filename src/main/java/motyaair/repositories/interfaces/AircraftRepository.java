package motyaair.repositories.interfaces;

import motyaair.models.Aircraft;


public interface AircraftRepository {

    Aircraft addAircraft(Aircraft aircraft);

    Aircraft getAircraftById(Long aircraftId);

    void deleteAircraft(Long aircraftId);

}
