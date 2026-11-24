package motyaair.repositories;

import motyaair.models.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {

    List<FlightSeat> findByFlightId(Long flightId);
}
