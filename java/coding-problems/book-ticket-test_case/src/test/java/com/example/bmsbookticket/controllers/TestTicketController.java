package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.BookTicketRequestDTO;
import com.example.bmsbookticket.dtos.BookTicketResponseDTO;
import com.example.bmsbookticket.dtos.ResponseStatus;
import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestTicketController {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatsRepository seatRepository;
    private User user;
    private List<ShowSeat> showSeats;

    @BeforeEach
    public void insertDummyData(){
        User u = new User();
        u.setName("Test User");
        u.setEmail("test@scaler.com");
        user = userRepository.save(u);

        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Test Movie");
        movie.setDescription("Test Description");

        Show show = new Show();
//        show.setMovie(movie);
        show.setStartTime(new Date());
        show.setEndTime(new Date());
        show = showRepository.save(show);

        Seat seat1 = new Seat();
        seat1.setName("1A");
        seat1.setSeatType(SeatType.GOLD);
        seat1 = seatRepository.save(seat1);

        Seat seat2 = new Seat();
        seat2.setName("1B");
        seat2.setSeatType(SeatType.GOLD);
        seat2 = seatRepository.save(seat2);

        Seat seat3 = new Seat();
        seat3.setName("2A");
        seat3.setSeatType(SeatType.SILVER);
        seat3 = seatRepository.save(seat3);

        Seat seat4 = new Seat();
        seat4.setName("2B");
        seat4.setSeatType(SeatType.SILVER);
        seat4 = seatRepository.save(seat4);

        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setSeat(seat1);
        showSeat1.setShow(show);
        showSeat1.setStatus(SeatStatus.AVAILABLE);
        showSeat1 = showSeatRepository.save(showSeat1);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setSeat(seat2);
        showSeat2.setShow(show);
        showSeat2.setStatus(SeatStatus.AVAILABLE);
        showSeat2 = showSeatRepository.save(showSeat2);

        ShowSeat showSeat3 = new ShowSeat();
        showSeat3.setSeat(seat3);
        showSeat3.setShow(show);
        showSeat3.setStatus(SeatStatus.AVAILABLE);
        showSeat3 = showSeatRepository.save(showSeat3);

        ShowSeat showSeat4 = new ShowSeat();
        showSeat4.setSeat(seat4);
        showSeat4.setShow(show);
        showSeat4.setStatus(SeatStatus.AVAILABLE);
        showSeat4 = showSeatRepository.save(showSeat4);

        showSeats = List.of(showSeat1, showSeat2, showSeat3, showSeat4);
    }

    @AfterEach
    public void cleanUp(){
        ticketRepository.deleteAll();
        showSeatRepository.deleteAll();
        showRepository.deleteAll();
        seatRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testBookTicket_1_Request_Success() throws Exception {
        BookTicketRequestDTO requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        BookTicketResponseDTO responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("Ticket should be stored in database"));
        List<ShowSeat> showSeats = showSeatRepository.findAll();
        assertEquals(SeatStatus.BLOCKED, showSeats.get(0).getStatus(), "Seat status should be BLOCKED");
        assertEquals(SeatStatus.BLOCKED, showSeats.get(1).getStatus(), "Seat status should be BLOCKED");
        assertEquals(SeatStatus.AVAILABLE, showSeats.get(2).getStatus(), "Seat status should be AVAILABLE");
        assertEquals(SeatStatus.AVAILABLE, showSeats.get(3).getStatus(), "Seat status should be AVAILABLE");
    }

    @Test
    public void testBookTicket_2_Request_Success_Non_overlapping_seats() throws Exception {
        BookTicketRequestDTO requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        BookTicketResponseDTO responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("Ticket should be stored in database"));

        requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(2).getId(), showSeats.get(3).getId()));
        requestDTO.setUserId(user.getId());
        responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getStatus(), "Status should be SUCCESS");
        ticket = responseDTO.getTicket();
        ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("Ticket should be stored in database"));

        assertNotNull(ticket, "Ticket should not be null");
        List<ShowSeat> showSeats = showSeatRepository.findAll();
        assertEquals(SeatStatus.BLOCKED, showSeats.get(0).getStatus(), "Seat status should be BLOCKED");
        assertEquals(SeatStatus.BLOCKED, showSeats.get(1).getStatus(), "Seat status should be BLOCKED");
        assertEquals(SeatStatus.BLOCKED, showSeats.get(2).getStatus(), "Seat status should be BLOCKED");
        assertEquals(SeatStatus.BLOCKED, showSeats.get(3).getStatus(), "Seat status should be BLOCKED");
    }

    @Test
    public void testBookTicket_NonExistingUser_Failure(){
        BookTicketRequestDTO requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(100);
        BookTicketResponseDTO responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }

    @Test
    public void testBookTicket_InvalidShowSeat_Failure(){
        BookTicketRequestDTO requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), 100));
        requestDTO.setUserId(user.getId());
        BookTicketResponseDTO responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }


    @Test
    public void testBookTicket_BookABookedTicket_Failure() throws Exception {
        BookTicketRequestDTO requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        BookTicketResponseDTO responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("Ticket should be stored in database"));

        requestDTO = new BookTicketRequestDTO();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }
}
