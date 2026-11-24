package motyaair.services;

import motyaair.models.Ticket;

public interface TicketService {

    Ticket createTicket(Ticket ticket, Long bookingId, Long passengerId);

    Ticket getTicket(Long ticketId);
}
