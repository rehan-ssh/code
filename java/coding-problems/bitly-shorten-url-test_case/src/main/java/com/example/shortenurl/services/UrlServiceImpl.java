package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.UrlNotFoundException;
import com.example.shortenurl.exceptions.UserNotFoundException;
import com.example.shortenurl.models.ShortenedUrl;
import com.example.shortenurl.models.UrlAccessLog;
import com.example.shortenurl.models.User;
import com.example.shortenurl.repositories.ShortenedUrlRepository;
import com.example.shortenurl.repositories.UrlAccessLogRepository;
import com.example.shortenurl.repositories.UserRepository;
import com.example.shortenurl.utils.ShortUrlGenerator;
import com.example.shortenurl.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService{

    private ShortenedUrlRepository shortenedUrlRepository;
    private UserRepository userRepository;
    private UrlAccessLogRepository urlAccessLogRepository;

    @Autowired
    public UrlServiceImpl(ShortenedUrlRepository shortenedUrlRepository, UserRepository userRepository, UrlAccessLogRepository urlAccessLogRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
        this.userRepository = userRepository;
        this.urlAccessLogRepository = urlAccessLogRepository;
    }


    @Override
    public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        String shortUrl = ShortUrlGenerator.generateShortUrl();
        long expiresAt = UserUtils.getExpiryTimeByUserPlan(user.getUserPlan());
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(url);
        shortenedUrl.setShortUrl(shortUrl);
        shortenedUrl.setExpiresAt(expiresAt);
        shortenedUrl.setUser(user);
        return shortenedUrlRepository.save(shortenedUrl);
    }

    @Override
    public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException {
        Optional<ShortenedUrl> shortenedUrlOptional = shortenedUrlRepository.findByShortUrl(shortUrl);
        if(shortenedUrlOptional.isEmpty()) {
            throw new UrlNotFoundException("Shortened url not found");
        }
        long currentTime = System.currentTimeMillis()/1000;
        if(currentTime > shortenedUrlOptional.get().getExpiresAt()) {
            throw new UrlNotFoundException("Shortened url expired");
        }
        ShortenedUrl shortenedUrl = shortenedUrlOptional.get();
        UrlAccessLog urlAccessLog = new UrlAccessLog();
        urlAccessLog.setShortenedUrl(shortenedUrl);
        urlAccessLog.setAccessedAt(currentTime);
        urlAccessLogRepository.save(urlAccessLog);
        return shortenedUrl.getOriginalUrl();
    }
}
