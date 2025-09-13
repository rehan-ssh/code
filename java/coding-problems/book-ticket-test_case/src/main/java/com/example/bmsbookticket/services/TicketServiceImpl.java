package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    private ShowSeatRepository showSeatRepository;

    private TicketRepository ticketRepository;

    private UserRepository userRepository;

    @Autowired
    public TicketServiceImpl(ShowSeatRepository showSeatRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws Exception{

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("Invalid user"));

        List<ShowSeat> showSeats = showSeatRepository.findShowSeatsByIdIn(showSeatIds);
        if(showSeats.size() != showSeatIds.size()){
            throw new Exception("Invalid show seats");
        }

        Show show = showSeats.stream().map(ShowSeat::getShow).findFirst().orElseThrow(() -> new Exception("Invalid show"));

        List<ShowSeat> bookedSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            if(!showSeat.getStatus().equals(SeatStatus.AVAILABLE)){
                bookedSeats.add(showSeat);
            }
        }

        if(!bookedSeats.isEmpty()){
            throw new Exception("Seats " + bookedSeats + " are not available");
        }

        showSeats.forEach(showSeat -> showSeat.setStatus(SeatStatus.BLOCKED));
        showSeatRepository.saveAll(showSeats);

        Ticket ticket = new Ticket();
        ticket.setSeats(showSeats.stream().map(ShowSeat::getSeat).toList());
        ticket.setShow(show);
        ticket.setTimeOfBooking(new Date());
        ticket.setUser(user);
        ticket.setStatus(TicketStatus.UNPAID);
        return ticketRepository.save(ticket);
    }
}
