package motyaair.services;

import motyaair.models.Flight;
import motyaair.models.FlightSeat;

import java.util.List;

public interface FlightService {

    Flight addFlight(Flight flight);

    List<Flight> getFlights();

    Flight getFlight(Long flightId);

    void addFlightSeats(List<FlightSeat> flightSeats, Long flightId);

    List<FlightSeat> getFlightSeats(Long flightId);

}


/*
POST	/flights	Создать рейс	ADMIN
GET	/flights	Найти рейсы	USER / ADMIN
GET	/flights/{id}	Информация о рейсе	USER / ADMIN
GET	/flights/{id}/seats	Посмотреть места и цены	USER / ADMIN

 */