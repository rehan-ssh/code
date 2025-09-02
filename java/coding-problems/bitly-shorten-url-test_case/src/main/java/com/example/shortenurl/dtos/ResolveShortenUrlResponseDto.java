package com.example.shortenurl.dtos;

import lombok.Data;

@Data
public class ResolveShortenUrlResponseDto {
    private String originalUrl;
    private ResponseStatus status;
}
