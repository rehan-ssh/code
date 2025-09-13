package com.example.bmsbookticket.dtos;

import com.example.bmsbookticket.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDTO {
    private ResponseStatus status;
    private Ticket ticket;
}
