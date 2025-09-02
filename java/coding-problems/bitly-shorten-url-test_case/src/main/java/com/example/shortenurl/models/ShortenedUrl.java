package com.example.shortenurl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "shortened_urls")
public class ShortenedUrl extends BaseModel{
    private String originalUrl;
    private String shortUrl;
    private long expiresAt;
    @ManyToOne
    private User user;
}
