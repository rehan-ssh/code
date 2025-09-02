package com.example.shortenurl.dtos;

import lombok.Data;

@Data
public class ShortenUrlRequestDto {
    private String originalUrl;
    private int userId;
}
