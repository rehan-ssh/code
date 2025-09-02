package com.example.bmsbookticket.dtos;

import com.example.bmsbookticket.models.Rating;
import lombok.Data;

@Data
public class RateMovieResponseDto {
    private ResponseStatus responseStatus;
    private Rating rating;
}
