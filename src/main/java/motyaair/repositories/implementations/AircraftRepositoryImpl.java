package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.Aircraft;
import motyaair.repositories.interfaces.AircraftRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AircraftRepositoryImpl implements AircraftRepository {

    private final JdbcTemplate jdbcTemplate;

    public AircraftRepositoryImpl(JdbcTemplate jdbcTemplate) {
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
    public Aircraft getAircraftById(Long aircraftId) {
        String sql = "SELECT * FROM aircrafts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, RowMappers::aircraftRowMapper, aircraftId);
    }

    @Override
    @Transactional
    public void deleteAircraft(Long aircraftId) {
        jdbcTemplate.update("DELETE FROM aircrafts WHERE id = ?", aircraftId);
    }
}
