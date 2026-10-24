package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.FlightSeat;
import motyaair.repositories.interfaces.FlightSeatRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class FlightSeatRepositoryImpl implements FlightSeatRepository {

    private final JdbcTemplate jdbcTemplate;

    public FlightSeatRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final String RETURN_FLIGHT_SEATS = """
            SELECT fs.id as id, fs.flight_id as flight_id, fs.seat_id as seat_id, fs.price as price, fs.status as status,
            
            s.seat_number as seat_number, s.seat_class as seat_class,
            a.id as aircraft_id, a.model as model,
            
            f.flight_number as flight_number, f.departure_time as departure_time, f.arrival_time as arrival_time,
            
            f.departure_airport_id as departure_airport_id,
            dep.airport_name as departure_airport_name,
            dep.code as departure_airport_code,
            dep.city as departure_airport_city,
            dep.country as departure_airport_country,
            
            f.arrival_airport_id as arrival_airport_id,
            arr.airport_name as arrival_airport_name,
            arr.code as arrival_airport_code,
            arr.city as arrival_airport_city,
            arr.country as arrival_airport_country
            
            FROM flight_seats as fs
            JOIN seats as s ON s.id = fs.seat_id
            JOIN aircrafts as a ON s.aircraft_id = a.id
            JOIN flights as f ON fs.flight_id = f.id
            JOIN airports as dep ON dep.id = f.departure_airport_id
            JOIN airports as arr ON arr.id = f.arrival_airport_id
            """;


    @Override
    @Transactional
    public FlightSeat addFlightSeat(FlightSeat flightSeat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO flight_seats(flight_id, seat_id, price, status) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(con-> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, flightSeat.getFlight().getId());
            ps.setLong(2, flightSeat.getSeat().getId());
            ps.setBigDecimal(3, flightSeat.getPrice());
            ps.setString(4, flightSeat.getStatus().name());
            return ps;
        });
        flightSeat.setId(keyHolder.getKey().longValue());
        return flightSeat;
    }


    @Override
    @Transactional
    public void addFlightSeats(Collection<FlightSeat> flightSeats) {
        String sql = "INSERT INTO flight_seats(flight_id, seat_id, price, status) VALUES(?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, flightSeats, flightSeats.size(), (ps, flightSeat) -> {
            ps.setLong(1, flightSeat.getFlight().getId());
            ps.setLong(2, flightSeat.getSeat().getId());
            ps.setBigDecimal(3, flightSeat.getPrice());
            ps.setString(4, flightSeat.getStatus().name());
        });
    }

    @Override
    public FlightSeat getFlightSeatById(Long flightSeatId) {
        return jdbcTemplate.queryForObject(RETURN_FLIGHT_SEATS + "\n" + "WHERE fs.id = ?",
                RowMappers::flightSeatRowMapper, flightSeatId);
    }

    public Collection<FlightSeat> getFlightSeats(Long flightId) {
        return jdbcTemplate.query(RETURN_FLIGHT_SEATS, RowMappers::flightSeatRowMapper);
    }
}
