package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.UrlNotFoundException;
import com.example.shortenurl.exceptions.UserNotFoundException;
import com.example.shortenurl.models.ShortenedUrl;

public interface UrlService {
    public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException;

    public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException;
}
