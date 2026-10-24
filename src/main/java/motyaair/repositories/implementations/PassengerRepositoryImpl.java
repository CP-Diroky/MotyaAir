package motyaair.repositories.implementations;

import motyaair.models.Passenger;
import motyaair.repositories.interfaces.PassengerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class PassengerRepositoryImpl implements PassengerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PassengerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Passenger addPassenger(Passenger passenger) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO passengers(first_name, last_name) VALUES(?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, passenger.getFirstName());
            ps.setString(2, passenger.getLastName());
            return ps;
        });

        passenger.setId(keyHolder.getKey().longValue());
        return passenger;
    }

    @Override
    @Transactional
    public void deletePassenger(Long passengerId) {
        String sql = "DELETE FROM passengers WHERE id =?";
        jdbcTemplate.update(sql, passengerId);
    }
}
