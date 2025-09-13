package com.example.shortenurl.dtos;

import lombok.Data;

@Data
public class ShortenUrlResponseDto {
    private String shortUrl;
    private long expiresAt;
    private ResponseStatus status;
}
