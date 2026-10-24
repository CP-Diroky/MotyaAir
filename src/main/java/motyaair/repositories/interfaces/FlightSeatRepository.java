package motyaair.repositories.interfaces;

import motyaair.models.FlightSeat;

import java.util.Collection;

public interface FlightSeatRepository {

    FlightSeat addFlightSeat(FlightSeat flightSeat);

    void addFlightSeats(Collection<FlightSeat> flightSeats);

    FlightSeat getFlightSeatById(Long flightSeatId);

    Collection<FlightSeat> getFlightSeats(Long flightId);
}
