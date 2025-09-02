package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Ticket save(Ticket ticket);
}
