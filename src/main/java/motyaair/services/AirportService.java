package motyaair.services;

import motyaair.models.Airport;

public interface AirportService {

    Airport addAirport(Airport airport);

    void deleteAirport(Long airportId);
}
