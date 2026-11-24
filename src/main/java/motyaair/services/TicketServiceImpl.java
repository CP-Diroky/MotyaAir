package motyaair.services;

import motyaair.exceptions.ConditionsNotMetException;
import motyaair.exceptions.NotFoundException;
import motyaair.models.Booking;
import motyaair.models.Passenger;
import motyaair.models.Ticket;
import motyaair.repositories.BookingRepository;
import motyaair.repositories.PassengerRepository;
import motyaair.repositories.TicketRepository;
import org.springframework.transaction.annotation.Transactional;

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, PassengerRepository passengerRepository,
                             BookingRepository bookingRepository) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public Ticket createTicket(Ticket ticket, Long bookingId, Long passengerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->  new NotFoundException("Бронирование с id = " + bookingId + " не найдено!"));
        if (!booking.isPaid()) {
            throw new ConditionsNotMetException("Бронирование не оплачено!");
        }

        if (passengerRepository.findById(passengerId).isEmpty()) {
            passengerRepository.save(ticket.getPassenger());
        }

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Билет с id = " + ticketId + " не найден!"));
    }
}
