package motyaair.repositories;

import motyaair.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightRepository extends JpaRepository<Flight, Long> {

}
