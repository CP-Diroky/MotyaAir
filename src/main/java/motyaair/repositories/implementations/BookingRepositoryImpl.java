package motyaair.repositories.implementations;

import motyaair.mappers.RowMappers;
import motyaair.models.Booking;
import motyaair.repositories.interfaces.BookingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class BookingRepositoryImpl implements BookingRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String RETURN_BOOKINGS = """
            SELECT b.id as id, b.expiration_date as expiration_date, b.is_paid as is_paid,
            fs.id as flight_seat_id, fs.seat_id as seat_id, fs.price as price, fs.status as status,
            u.id as user_id, u.first_name as first_name, u.last_name as last_name, u.email as email
            
            s.seat_number as seat_number,
            s.seat_class as seat_class,
            s.aircraft_id as aircraft_id,
            a.model as model,
            
            f.id as flight_id, f.flight_number as flight_number, f.aircraft_id as aircraft_id, a.model as model,
            
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
            
            f.departure_time, f.arrival_time
            
            FROM bookings as b
            JOIN users as u ON b.user_id = u.id
            JOIN flight_seats as fs ON b.flight_seat_id = fs.id
            JOIN seats as s ON fs.seat_id = s.id
            JOIN aircrafts as a ON s.aircraft_id = a.id
            JOIN flights as f ON fs.flight_id = f.id
            JOIN airports as dep ON f.departure_airport_id = dep.id
            JOIN airports as arr ON f.arrival_airport_id = arr.id
            """;

    public BookingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Booking addBooking(Booking booking) { // Добавить изменение статуса fs
        String sql = "INSERT INTO bookings(flight_seat_id, user_id, expiration_date, is_paid) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, booking.getFlightSeat().getId());
            ps.setLong(2, booking.getUser().getId());
            ps.setObject(3, booking.getExpirationDate());
            ps.setBoolean(4, booking.getIsPaid());
            return ps;
        });
        booking.setId(keyHolder.getKey().longValue());
        booking.setIsPaid(false);

        return booking;
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return jdbcTemplate.queryForObject(RETURN_BOOKINGS + "\n" + "WHERE b.id = ?",
                RowMappers::bookingRowMapper, bookingId);
    }

    @Override
    public Collection<Booking> getUserBookings(Long userId) {
        return jdbcTemplate.query(RETURN_BOOKINGS + "\n" + "WHERE u.id = ?", RowMappers::bookingRowMapper, userId);
    }

    @Override
    @Transactional
    public Booking payBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setIsPaid(true);
        jdbcTemplate.update("UPDATE bookings SET is_paid = true WHERE id = ?", bookingId);
        return booking;
    }


}
