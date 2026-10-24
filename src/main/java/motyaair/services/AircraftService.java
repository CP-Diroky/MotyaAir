package motyaair.services;

import motyaair.exceptions.NotFoundException;
import motyaair.mappers.RowMappers;
import motyaair.models.Aircraft;
import motyaair.models.Seat;
import motyaair.repositories.interfaces.AircraftRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Service
public class AircraftService implements AircraftRepository {

    private final JdbcTemplate jdbcTemplate;

    public AircraftService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Aircraft addAircraft(Aircraft aircraft) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO aircrafts(model) VALUES (?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, aircraft.getModel());
            return ps;

        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        aircraft.setId(id);

        return aircraft;

    }


    @Override
    @Transactional
    public void addSeatsToAircraft(Long aircraftId, Collection<Seat> seats) {

        try {
            Aircraft aircraft = jdbcTemplate.queryForObject("SELECT * FROM aircrafts WHERE id = ?",
                    RowMappers::aircraftRowMapper, aircraftId);

            jdbcTemplate.batchUpdate("INSERT INTO seats (seat_number, aircraft_id, seat_class) VALUES (?, ?, ?)",
                    seats, seats.size(), (ps, seat) -> {
                        ps.setString(1, seat.getSeatNumber());
                        ps.setLong(2, aircraftId);
                        ps.setString(3, seat.getSeatClass().toString());
                    });
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Самолёт с id = " + aircraftId + " не найден!");
        }

    }

    @Override
    @Transactional
    public void deleteAircraft(Long aircraftId) {
        jdbcTemplate.update("DELETE FROM aircrafts WHERE id = ?", aircraftId);
    }

}


