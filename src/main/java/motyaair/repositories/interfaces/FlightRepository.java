package motyaair.repositories.interfaces;

import motyaair.models.Flight;

import java.util.Collection;

public interface FlightRepository {

    Flight addFlight(Flight flight);

    Collection<Flight> getFlights();

    Flight getFlightById(Long flightId);

}


