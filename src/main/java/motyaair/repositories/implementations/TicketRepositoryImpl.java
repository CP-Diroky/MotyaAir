package motyaair.repositories.implementations;

import motyaair.models.Ticket;
import motyaair.repositories.interfaces.TicketRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class TicketRepositoryImpl implements TicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public TicketRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    Ticket createTicket(Ticket ticket) {

    }

    Ticket getTicketById(Long ticketId, Long userId);
}
