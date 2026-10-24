package motyaair.repositories.interfaces;

import motyaair.models.Airport;

import java.util.Collection;

public interface AirportRepository {

    Airport addAirport(Airport airport);

    Collection<Airport> getAirports();

    Airport getAirportById(Long id);


}
