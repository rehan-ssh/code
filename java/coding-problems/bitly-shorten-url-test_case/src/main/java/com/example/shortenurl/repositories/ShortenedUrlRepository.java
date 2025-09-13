package com.example.shortenurl.repositories;

import com.example.shortenurl.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Integer> {

    Optional<ShortenedUrl> findByShortUrl(String shortUrl);
}
