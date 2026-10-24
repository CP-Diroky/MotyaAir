package motyaair.repositories.interfaces;

import motyaair.models.Ticket;

public interface TicketRepository {

    Ticket createTicket(Ticket ticket);

    Ticket getTicketById(Long ticketId, Long userId);
}
