package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.Flight;
import motyaair.repositories.interfaces.FlightRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class FlightRepositoryImpl implements FlightRepository {

    private final JdbcTemplate jdbcTemplate;

    public FlightRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    @Override
    @Transactional
    public Flight addFlight(Flight flight) {
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
        return jdbcTemplate.queryForObject(RETURN_FLIGHTS + "\n" + "WHERE id = ?",
                RowMappers::flightRowMapper, flightId);
    }
}
