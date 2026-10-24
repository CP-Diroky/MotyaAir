package motyaair.services;

import motyaair.mappers.RowMappers;
import motyaair.models.*;
import motyaair.repositories.interfaces.FlightRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FlightService implements FlightRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String RETURN_FLIGHTS = """ 
            SELECT f.id as id, f.flight_number as flight_number, f.aircraft_id as aircraft_id, a.model as model,
            
            f.departure_airport_id as departure_airport_id,
            dep.airport_name as departure_airport_name,
            dep.code as departure_airport_code,
            dep.city as departure_airport_city,
            dep.country as departure_airport_country,
            
            f.arrival_airport_id as arrival_airport_id,
            arr.airport_name as arrival_airport_name,
            arr.code as arrival_airport_code,
            arr.city as arrival_airport_city,
            arr.country as arrival_airport_country,
            
            f.departure_time as departure_time, f.arrival_time as arrival_time
            
            FROM flights as f
            JOIN aircrafts as a ON f.aircraft_id = a.id
            JOIN airports as dep ON f.departure_airport_id = dep.id
            JOIN airports as arr ON f.arrival_airport_id = arr.id
            """;

    private static final String RETURN_SEATS = """
            SELECT s.id as id, s.seat_number as seat_number, s.seat_class as seat_class
            
            s.aircraft_id as aircraft_id,
            a.model as model
            
            FROM seats as s
            JOIN aircrafts as a ON a.id = s.aircraft_id
            """;

    private static final String RETURN_FLIGHT_SEATS = """
            SELECT fs.id as id, fs.seat_id as seat_id, fs.price as price, fs.status as status
            
            s.seat_number as seat_number,
            s.seat_class as seat_class,
            s.aircraft_id as aircraft_id,
            a.model as model
            
            
            FROM flight_seats as fs
            JOIN seats as s ON s.id = fs.seat_id
            JOIN aircrafts as a ON a.id = s.aircraft_id
            WHERE fs.flight_id = ?
            """;



    public FlightService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Flight addFlight(Flight flight) {


        if (flight.getDepartureAirport().getId() == null || flight.getArrivalAirport().getId() == null) {
            // throw new ConditionsNotMetException
        } else if (flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            // throw new ConditionsNotMetException
        }

        try {
            Airport departureAirport = jdbcTemplate.queryForObject("SELECT * FROM airports WHERE id = ?",
                    RowMappers::airportRowMapper, flight.getDepartureAirport().getId());
            Airport arrivalAirport = jdbcTemplate.queryForObject("SELECT * FROM airports WHERE id = ?",
                    RowMappers::airportRowMapper, flight.getArrivalAirport().getId());
            Aircraft aircraft = jdbcTemplate.queryForObject("SELECT * FROM aircrafts WHERE id = ?",
                    RowMappers::aircraftRowMapper, flight.getAircraft().getId());
        } catch (Exception e) {
            //throw new NotFoundException
        }

        String sql = "INSERT INTO flights(flight_number, aircraft_id, departure_airport_id , arrival_airport_id,"
                + "departure_time, arrival_time VALUES( ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, flight.getFlightNumber());
            ps.setLong(2, flight.getAircraft().getId());
            ps.setLong(3, flight.getDepartureAirport().getId());
            ps.setLong(4, flight.getArrivalAirport().getId());
            ps.setObject(5, flight.getDepartureTime());
            ps.setObject(6, flight.getArrivalTime());
            return ps;
        }, keyHolder);

        flight.setId(keyHolder.getKey().longValue());

        return flight;

    }

    @Override
    public Collection<Flight> getFlights() {
        return jdbcTemplate.query(RETURN_FLIGHTS, RowMappers::flightRowMapper);
    }

    @Override
    public Flight getFlightById(Long flightId) {
        return jdbcTemplate.queryForObject(RETURN_FLIGHTS +
                "\n" + "WHERE f.id = ?", RowMappers::flightRowMapper, flightId);
    }

    @Override
    @Transactional
    public void addFlightSeat(Long flightId, FlightSeat flightSeat) {
        try {
            Flight flight = jdbcTemplate.queryForObject(RETURN_FLIGHTS +
                    "\n" + "WHERE f.id = ?", RowMappers::flightRowMapper, flightId);
            Seat seat = jdbcTemplate.queryForObject(RETURN_SEATS + "\n" + "WHERE s.id = ?",
                    RowMappers::seatRowMapper, flightSeat.getSeat().getId());
        } catch (Exception e) {
            //throw new NotFoundException
        }

        flightSeat.setStatus(FlightSeatStatus.AVAILABLE);


        String sql = "INSERT INTO flight_seats(flight_id, seat_id, price, status) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(sql, flightId, flightSeat.getSeat().getId(), flightSeat.getPrice(),
                flightSeat.getStatus().toString());

    }

    @Override
    @Transactional
    public void addFlightSeats(Long flightId, Collection<FlightSeat> flightSeats) {
        try {
            Flight flight = jdbcTemplate.queryForObject(RETURN_FLIGHTS +
                    "\n" + "WHERE f.id = ?", RowMappers::flightRowMapper, flightId);
        } catch (Exception e) {
            //throw new NotFoundException
        }

        if (flightSeats.isEmpty()) return;

        flightSeats.forEach(flightSeat -> flightSeat.setStatus(FlightSeatStatus.AVAILABLE));

        String sql = "INSERT INTO flight_seats(flight_id, seat_id, price, status) VALUES(?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, flightSeats, flightSeats.size(), (ps, flightSeat) -> {
            ps.setLong(1, flightId);
            ps.setLong(2, flightSeat.getSeat().getId());
            ps.setObject(3, flightSeat.getPrice());
            ps.setString(4, flightSeat.getStatus().toString());
        });

    }

    @Override
    public Collection<FlightSeat> getFlightSeats(Long flightId) {
        List<FlightSeat> flightSeats = new ArrayList<>();
        try {
            Flight flight = jdbcTemplate.queryForObject(RETURN_FLIGHTS +
                    "\n" + "WHERE f.id = ?", RowMappers::flightRowMapper, flightId);
            flightSeats = jdbcTemplate.query(RETURN_FLIGHT_SEATS,
                    RowMappers::flightSeatRowMapper, flightId);
            flightSeats.forEach(flightseat -> flightseat.setFlight(flight));
            return flightSeats;
        } catch (Exception e) {
            //throw new NotFoundException
        }
        return flightSeats;
    }




}
