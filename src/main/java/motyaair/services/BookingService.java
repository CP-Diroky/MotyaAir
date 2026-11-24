package motyaair.services;

import motyaair.models.Booking;

import java.util.List;

public interface BookingService {

    Booking addBooking(Booking booking, Long flightSeatId);

    Booking getBooking(Long bookingId);

    List<Booking> getUsersBookings(Long userId);

    Booking payBooking(Long bookingId);
}



