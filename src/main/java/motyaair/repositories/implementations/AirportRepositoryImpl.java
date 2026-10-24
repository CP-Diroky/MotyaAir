package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.Airport;
import motyaair.repositories.interfaces.AirportRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class AirportRepositoryImpl implements AirportRepository {

    private final JdbcTemplate jdbcTemplate;

    public AirportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Airport addAirport(Airport airport) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO airports(airport_name, code, city, country)"
                    + " VALUES (?, ?, ?, ?)",  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, airport.getAirportName());
            ps.setString(2, airport.getCode());
            ps.setString(3, airport.getCity());
            ps.setString(4, airport.getCountry());
            return ps;
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();

        airport.setId(key);

        return airport;

    }

    @Override
    public Collection<Airport> getAirports() {
        String sql = "SELECT * FROM airports";
        return jdbcTemplate.query(sql, RowMappers::airportRowMapper);
    }

    @Override
    public Airport getAirportById(Long id) {
        String sql = "SELECT * FROM airports WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, RowMappers::airportRowMapper, id);
    }
}
