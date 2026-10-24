package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.Seat;
import motyaair.repositories.interfaces.SeatRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class SeatRepositoryImpl implements SeatRepository {

    private final JdbcTemplate jdbcTemplate;

    public SeatRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public Seat addSeat(Seat seat) {
       String sql = "INSERT INTO seats(seat_number, aircraft_id, seat_class) VALUES(?, ?, ?)";
       KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seat.getSeatNumber());
            ps.setLong(2, seat.getAircraft().getId());
            ps.setString(3, seat.getSeatClass().name());
            return ps;
        });
        seat.setId(keyHolder.getKey().longValue());
        return seat;
    }




    @Override
    @Transactional
    public void addSeats(Long aircraftId, Collection<Seat> seats) {
        jdbcTemplate.batchUpdate("INSERT INTO seats (seat_number, aircraft_id, seat_class) VALUES (?, ?, ?)",
                seats, seats.size(), (ps, seat) -> {
                    ps.setString(1, seat.getSeatNumber());
                    ps.setLong(2, aircraftId);
                    ps.setString(3, seat.getSeatClass().name());
                });
    }


    @Override
    public Seat getSeatById(Long seatId) {
        String sql = """
                SELECT s.id as id, s.seat_number as seat_number, s.seat_class as seat_class,
                
                a.aircraft_id as aircraft_id,
                a.model as model
                
                FROM seats as s
                JOIN aircrafts as ON a.id = s.aircraft_id
                WHERE s.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, RowMappers::seatRowMapper, seatId);
    }

    @Override
    @Transactional
    public void deleteSeat(Long seatId) {
        String sql = "DELETE FROM seats WHERE id = ?";
        jdbcTemplate.update(sql, seatId);
    }
}
