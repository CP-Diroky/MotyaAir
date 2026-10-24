package motyaair.repositories.interfaces;

import motyaair.models.Seat;

import java.util.Collection;

public interface SeatRepository {

    Seat addSeat(Seat seat);

    void addSeats(Long aircraft, Collection<Seat> seats);

    Seat getSeatById(Long seatId);

    void deleteSeat(Long seatId);
}
