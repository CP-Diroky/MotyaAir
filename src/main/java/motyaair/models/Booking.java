package motyaair.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "flight_seat_id")
    private FlightSeat flightSeat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "is_paid")
    private Boolean isPaid;

}
