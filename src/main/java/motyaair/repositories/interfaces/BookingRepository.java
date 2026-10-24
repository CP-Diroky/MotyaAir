package motyaair.repositories.interfaces;

import motyaair.models.Booking;

import java.util.Collection;

public interface BookingRepository {

    Booking addBooking(Booking booking);

    Booking getBookingById(Long bookingId);

    Collection<Booking> getUserBookings(Long userId);

    Booking payBooking(Long bookingId);

}
