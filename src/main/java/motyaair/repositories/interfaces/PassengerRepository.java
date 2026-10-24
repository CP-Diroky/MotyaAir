package motyaair.repositories.interfaces;

import motyaair.models.Passenger;

public interface PassengerRepository {

    Passenger addPassenger(Passenger passenger);

    void deletePassenger(Long passengerId);
}
