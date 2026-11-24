package motyaair.services;


import motyaair.exceptions.ConditionsNotMetException;
import motyaair.exceptions.NotFoundException;
import motyaair.models.Booking;
import motyaair.models.FlightSeat;
import motyaair.models.FlightSeatStatus;
import motyaair.repositories.BookingRepository;
import motyaair.repositories.FlightSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightSeatRepository flightSeatRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, FlightSeatRepository flightSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.flightSeatRepository = flightSeatRepository;
    }

    @Override
    @Transactional
    public Booking addBooking(Booking booking, Long flightSeatId) {
        FlightSeat flightSeat = flightSeatRepository.findById(flightSeatId)
                .orElseThrow(() -> new NotFoundException("Место с id = " + flightSeatId + " не найдено!"));
        if (flightSeat.getStatus() == FlightSeatStatus.HELD || flightSeat.getStatus() == FlightSeatStatus.BOOKED) {
            throw new ConditionsNotMetException("Место уже занято!");
        }
        flightSeat.setStatus(FlightSeatStatus.HELD);
        booking.setFlightSeat(flightSeat);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->  new NotFoundException("Бронирование с id = " + bookingId + " не найдено!"));
    }


    @Override
    public List<Booking> getUsersBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Booking payBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->  new NotFoundException("Бронирование с id = " + bookingId + " не найдено!"));
        booking.setPaid(true);
        booking.getFlightSeat().setStatus(FlightSeatStatus.BOOKED);
        flightSeatRepository.save(booking.getFlightSeat());
        return booking;
    }
}
