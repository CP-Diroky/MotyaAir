package motyaair.mappers;

import motyaair.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RowMappers {


    public static Airport airportRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Airport airport = new Airport();
        airport.setId(rs.getLong(1));
        airport.setAirportName(rs.getString(2));
        airport.setCode(rs.getString(3));
        airport.setCity(rs.getString(4));
        airport.setCountry(rs.getString(5));
        return airport;
    }

    public static Aircraft aircraftRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(rs.getLong("id"));
        aircraft.setModel(rs.getString("model"));
        return aircraft;
    }

    public static Flight flightRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getLong("id"));
        flight.setFlightNumber(rs.getString("flight_number"));

        Aircraft aircraft = new Aircraft();
        aircraft.setId(rs.getLong("aircraft_id"));
        aircraft.setModel(rs.getString("model"));

        Airport departureAirport = new Airport();
        departureAirport.setId(rs.getLong("departure_airport_id"));
        departureAirport.setAirportName(rs.getString("departure_airport_name"));
        departureAirport.setCode(rs.getString("departure_airport_code"));
        departureAirport.setCity(rs.getString("departure_airport_city"));
        departureAirport.setCountry(rs.getString("departure_airport_country"));

        Airport arrivalAirport = new Airport();
        arrivalAirport.setId(rs.getLong("arrival_airport_id"));
        arrivalAirport.setAirportName(rs.getString("arrival_airport_name"));
        arrivalAirport.setCode(rs.getString("arrival_airport_code"));
        arrivalAirport.setCity(rs.getString("arrival_airport_city"));
        arrivalAirport.setCountry(rs.getString("arrival_airport_country"));

        flight.setAircraft(aircraft);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(rs.getObject("departure_time", LocalDateTime.class));
        flight.setArrivalTime(rs.getObject("arrival_time", LocalDateTime.class));
        return flight;
    }

    public static Seat seatRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Seat seat = new Seat();
        seat.setId(rs.getLong("id"));
        seat.setSeatNumber(rs.getString("seat_number"));

        Aircraft aircraft = new Aircraft();
        aircraft.setId(rs.getLong("aircraft_id"));
        aircraft.setModel(rs.getString("model"));

        seat.setAircraft(aircraft);
        seat.setSeatClass(SeatClass.valueOf(rs.getString("seat_class")));
        return seat;
    }

    public static FlightSeat flightSeatRowMapper(ResultSet rs, int rowNum) throws SQLException {
        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setId(rs.getLong("id"));

        Seat seat = new Seat();
        seat.setId(rs.getLong("seat_id"));
        seat.setSeatNumber(rs.getString("seat_number"));

        Aircraft aircraft = new Aircraft();
        aircraft.setId(rs.getLong("aircraft_id"));
        aircraft.setModel(rs.getString("model"));

        seat.setAircraft(aircraft);
        seat.setSeatClass(SeatClass.valueOf(rs.getString("seat_class")));

        flightSeat.setSeat(seat);
        flightSeat.setPrice(rs.getBigDecimal("price"));
        flightSeat.setStatus(FlightSeatStatus.valueOf(rs.getString("status")));

        Flight flight = new Flight();
        flight = flightRowMapper(rs, rowNum);
        flight.setId(rs.getLong("flight_id"));
        flightSeat.setFlight(flight);

        return flightSeat;
    }

    public static User userRowMapper(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    public static Booking bookingRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getLong("id"));
        booking.setExpirationDate(rs.getObject("expiration_date", LocalDateTime.class));
        booking.setIsPaid(rs.getBoolean("is_paid"));

        User user = userRowMapper(rs, rowNum);
        user.setId(rs.getLong("user_id"));
        booking.setUser(user);

        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setId(rs.getLong("flight_seat_id"));

        Seat seat = new Seat();
        seat.setId(rs.getLong("seat_id"));
        seat.setSeatNumber(rs.getString("seat_number"));

        Aircraft aircraft = new Aircraft();
        aircraft.setId(rs.getLong("aircraft_id"));
        aircraft.setModel(rs.getString("model"));

        seat.setAircraft(aircraft);
        seat.setSeatClass(SeatClass.valueOf(rs.getString("seat_class")));

        flightSeat.setSeat(seat);
        flightSeat.setPrice(rs.getBigDecimal("price"));
        flightSeat.setStatus(FlightSeatStatus.valueOf(rs.getString("status")));

        booking.setFlightSeat(flightSeat);

        Flight flight = flightRowMapper(rs, rowNum);
        flight.setId(rs.getLong("flight_id"));
        flightSeat.setFlight(flight);

        return booking;
    }



}

