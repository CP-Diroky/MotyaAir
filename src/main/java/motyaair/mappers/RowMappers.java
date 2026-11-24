package motyaair.mappers;

import lombok.experimental.UtilityClass;
import motyaair.dto.*;
import motyaair.dto.UserDto.UserDto;
import motyaair.models.*;

import java.util.List;


@UtilityClass
public class RowMappers {


    public static AircraftDto aircraftDto(Aircraft aircraft) {
        AircraftDto aircraftDto = new AircraftDto();
        aircraftDto.setModel(aircraft.getModel());
        return aircraftDto;
    }

    public static Aircraft aircraft(AircraftDto aircraftDto) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(aircraftDto.getModel());
        return aircraft;
    }

    public static AirportDto airportDto(Airport airport) {
        AirportDto airportDto = new AirportDto();
        airportDto.setAirportName(airport.getAirportName());
        airportDto.setCode(airport.getCode());
        airportDto.setCity(airport.getCity());
        airportDto.setCountry(airport.getCountry());
        return airportDto;
    }

    public static Airport airport(AirportDto airportDto) {
        Airport airport = new Airport();
        airport.setAirportName(airportDto.getAirportName());
        airport.setCode(airportDto.getCode());
        airport.setCity(airportDto.getCity());
        airport.setCountry(airportDto.getCountry());
        return airport;
    }

    public static BookingDto bookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setFlightSeatDto(flightSeatDto(booking.getFlightSeat()));
        bookingDto.setUserDto(userDto(booking.getUser()));
        bookingDto.setPaid(booking.isPaid());
        bookingDto.setExpirationDate(booking.getExpirationDate());
        return bookingDto;
    }

    public static Booking booking(BookingDto bookingDto) {
        Booking booking = new Booking();
        if (bookingDto.getFlightSeatDto() != null) {
            booking.setFlightSeat(flightSeat(bookingDto.getFlightSeatDto()));
        }
        booking.setUser(user(bookingDto.getUserDto()));
        booking.setPaid(bookingDto.isPaid());
        booking.setExpirationDate(bookingDto.getExpirationDate());
        return booking;
    }

    public static List<BookingDto> bookingDtoList(List<Booking> bookingList) {
       return bookingList.stream().map(RowMappers::bookingDto).toList();
    }

    public static FlightDto flightDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setDepartureAirport(airportDto(flight.getDepartureAirport()));
        flightDto.setArrivalAirport(airportDto(flight.getArrivalAirport()));
        flightDto.setAircraft(aircraftDto(flight.getAircraft()));
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setArrivalTime(flight.getArrivalTime());
        return flightDto;
    }

    public static Flight flight(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setDepartureAirport(airport(flightDto.getDepartureAirport()));
        flight.setArrivalAirport(airport(flightDto.getArrivalAirport()));
        flight.setAircraft(aircraft(flightDto.getAircraft()));
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        return flight;
    }

    public static List<FlightDto> flightDtoList(List<Flight> flights) {
        return flights.stream().map(RowMappers::flightDto).toList();
    }


    public static FlightSeatDto flightSeatDto(FlightSeat flightSeat) {
        FlightSeatDto flightSeatDto = new FlightSeatDto();
        flightSeatDto.setFlight(flightDto(flightSeat.getFlight()));
        flightSeatDto.setSeat(seatDto(flightSeat.getSeat()));
        flightSeatDto.setPrice(flightSeat.getPrice());
        flightSeatDto.setStatus(flightSeat.getStatus());
        return flightSeatDto;
    }

    public static FlightSeat flightSeat(FlightSeatDto flightSeatDto) {
        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setFlight(flight(flightSeatDto.getFlight()));
        flightSeat.setSeat(seat(flightSeatDto.getSeat()));
        flightSeat.setPrice(flightSeatDto.getPrice());
        flightSeat.setStatus(flightSeatDto.getStatus());
        return flightSeat;
    }

    public static List<FlightSeatDto> flightSeatDtoList(List<FlightSeat> flightSeatList) {
        return flightSeatList.stream().map(RowMappers::flightSeatDto).toList();
    }

    public static List<FlightSeat> flightSeatList(List<FlightSeatDto> flightSeatDtoList) {
        return flightSeatDtoList.stream().map(RowMappers::flightSeat).toList();
    }


    public static PassengerDto passengerDto(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        return passengerDto;
    }

    public static Passenger passenger(PassengerDto passengerDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        return passenger;
    }

    public static SeatDto seatDto(Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setAircraft(aircraftDto(seat.getAircraft()));
        seatDto.setSeatClass(seat.getSeatClass());
        return seatDto;
    }

    public static Seat seat(SeatDto seatDto) {
        Seat seat = new Seat();
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setAircraft(aircraft(seatDto.getAircraft()));
        seat.setSeatClass(seatDto.getSeatClass());
        return seat;
    }

    public static List<Seat> seatList(List<SeatDto> seatDtos) {
        return seatDtos.stream().map(RowMappers::seat).toList();
    }

    public static List<SeatDto> seatDtoList(List<Seat> seats) {
        return seats.stream().map(RowMappers::seatDto).toList();
    }

    public static TicketDto ticketDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketNumber(ticket.getTicketNumber());
        ticketDto.setPassenger(passengerDto(ticket.getPassenger()));
        ticketDto.setBooking(bookingDto(ticket.getBooking()));
        return ticketDto;
    }

    public static Ticket ticket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketDto.getTicketNumber());
        ticket.setPassenger(passenger(ticketDto.getPassenger()));
        ticket.setBooking(booking(ticketDto.getBooking()));
        return ticket;
    }

    public static UserDto userDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }

    public static User user(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }


}

