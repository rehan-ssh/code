package com.example.bmsbookticket.dtos;

import com.example.bmsbookticket.models.Show;
import lombok.Data;

@Data
public class CreateShowResponseDTO {
    private ResponseStatus responseStatus;
    private Show show;
}
