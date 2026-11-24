package motyaair.controllers.user_level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import motyaair.dto.BookingDto;
import motyaair.mappers.RowMappers;
import motyaair.services.BookingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto addBooking(@RequestBody @Valid BookingDto bookingDto, @RequestParam @Positive Long flightSeatId) {
        return RowMappers.bookingDto(bookingService.addBooking(RowMappers.booking(bookingDto), flightSeatId));
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable @Positive Long bookingId) {
        return RowMappers.bookingDto(bookingService.getBooking(bookingId));
    }

    @GetMapping
    public List<BookingDto> getUsersBookings(@RequestParam @Positive Long userId) {
        return RowMappers.bookingDtoList(bookingService.getUsersBookings(userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingDto payBooking(@PathVariable @Positive Long bookingId) {
        return RowMappers.bookingDto(bookingService.payBooking(bookingId));
    }


}
