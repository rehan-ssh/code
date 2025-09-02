package com.example.shortenurl.controllers;

import com.example.shortenurl.dtos.*;
import com.example.shortenurl.models.ShortenedUrl;
import com.example.shortenurl.models.UrlAccessLog;
import com.example.shortenurl.models.User;
import com.example.shortenurl.models.UserPlan;
import com.example.shortenurl.repositories.ShortenedUrlRepository;
import com.example.shortenurl.repositories.UrlAccessLogRepository;
import com.example.shortenurl.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUrlController {

    @Autowired
    private UrlController urlController;
    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlAccessLogRepository urlAccessLogRepository;

    private User freeUser, teamUser, businessUser, enterpriseUser;

    @BeforeEach
    public void setup() {
        urlAccessLogRepository.deleteAll();
        shortenedUrlRepository.deleteAll();
        userRepository.deleteAll();

        freeUser = new User();
        freeUser.setName("Free User");
        freeUser.setUserPlan(UserPlan.FREE);
        freeUser = userRepository.save(freeUser);

        teamUser = new User();
        teamUser.setName("Team User");
        teamUser.setUserPlan(UserPlan.TEAM);
        teamUser = userRepository.save(teamUser);

        businessUser = new User();
        businessUser.setName("Business User");
        businessUser.setUserPlan(UserPlan.BUSINESS);
        businessUser = userRepository.save(businessUser);

        enterpriseUser = new User();
        enterpriseUser.setName("Enterprise User");
        enterpriseUser.setUserPlan(UserPlan.ENTERPRISE);
        enterpriseUser = userRepository.save(enterpriseUser);
    }

    public long getCurrentTime(){
        long currentTime = System.currentTimeMillis() / 1000;
        currentTime -= 50; //To account for the time taken by the test
        return currentTime;
    }
    @Test
    public void testShortenUrl_freeUser_Success() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(freeUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        assertEquals(ResponseStatus.SUCCESS, shortenUrlResponseDto.getStatus(), "Status should be success");
        assertNotNull(shortenUrlResponseDto.getShortUrl(), "Short url should not be null");
        long currentTime = getCurrentTime();
        assertTrue(currentTime + 86400 <= shortenUrlResponseDto.getExpiresAt(), "Expires at should be 1 day from now");
    }

    @Test
    public void testShortenUrl_teamUser_Success() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(teamUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        assertEquals(ResponseStatus.SUCCESS, shortenUrlResponseDto.getStatus(), "Status should be success");
        assertNotNull(shortenUrlResponseDto.getShortUrl(), "Short url should not be null");
        long currentTime = getCurrentTime();
        assertTrue(currentTime + (86400 * 7) <= shortenUrlResponseDto.getExpiresAt(), "Expires at should be 1 day from now");
    }

    @Test
    public void testShortenUrl_businessUser_Success() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(businessUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        assertEquals(ResponseStatus.SUCCESS, shortenUrlResponseDto.getStatus(), "Status should be success");
        assertNotNull(shortenUrlResponseDto.getShortUrl(), "Short url should not be null");
        long currentTime = getCurrentTime();
        assertTrue(currentTime + (86400 * 30) <= shortenUrlResponseDto.getExpiresAt(), "Expires at should be 1 day from now");
    }

    @Test
    public void testShortenUrl_enterpriseUser_Success() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(enterpriseUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        assertEquals(ResponseStatus.SUCCESS, shortenUrlResponseDto.getStatus(), "Status should be success");
        assertNotNull(shortenUrlResponseDto.getShortUrl(), "Short url should not be null");
        long currentTime = getCurrentTime();
        assertTrue(currentTime + (86400 * 365) <= shortenUrlResponseDto.getExpiresAt(), "Expires at should be 1 day from now");
    }

    @Test
    public void testShortenUrl_Failure() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(1000);
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        assertEquals(ResponseStatus.FAILURE, shortenUrlResponseDto.getStatus(), "Status should be success");
        assertNull(shortenUrlResponseDto.getShortUrl(), "Short url should not be null");
    }


    @Test
    public void testGetOriginalUrl_Success() {
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(freeUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        String shortUrl = shortenUrlResponseDto.getShortUrl();

        ResolveShortenUrlRequestDto resolveShortenUrlRequestDto = new ResolveShortenUrlRequestDto();
        resolveShortenUrlRequestDto.setShortenUrl(shortUrl);
        ResolveShortenUrlResponseDto responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals("https://www.google.com", responseDto.getOriginalUrl(), "Original url should be same");

        List<UrlAccessLog> urlAccessLogList = urlAccessLogRepository.findAll();
        assertEquals(1, urlAccessLogList.size(), "Url access log size should be 1");
        urlAccessLogList.forEach(urlAccessLog -> {
            assertEquals(shortUrl, urlAccessLog.getShortenedUrl().getShortUrl(), "Short url should be same");
            long currentTime = getCurrentTime();
            assertTrue(currentTime - 10 <= urlAccessLog.getAccessedAt(), "Accessed at should be 10 seconds from now");
        });


        responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals("https://www.google.com", responseDto.getOriginalUrl(), "Original url should be same");

        responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals("https://www.google.com", responseDto.getOriginalUrl(), "Original url should be same");

        responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals("https://www.google.com", responseDto.getOriginalUrl(), "Original url should be same");

        urlAccessLogList = urlAccessLogRepository.findAll();
        assertEquals(4, urlAccessLogList.size(), "Url access log size should be 1");
        urlAccessLogList.forEach(urlAccessLog -> {
            assertEquals(shortUrl, urlAccessLog.getShortenedUrl().getShortUrl(), "Short url should be same");
            long currentTime = getCurrentTime();
            assertTrue(currentTime - 10 <= urlAccessLog.getAccessedAt(), "Accessed at should be 10 seconds from now");
        });
    }

    @Test
    public void testGetOriginalUrl_UrlNotFound_Failure() {
        ResolveShortenUrlRequestDto resolveShortenUrlRequestDto = new ResolveShortenUrlRequestDto();
        resolveShortenUrlRequestDto.setShortenUrl("Ax1erT");
        ResolveShortenUrlResponseDto responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus(), "Status should be failure");
        assertNull(responseDto.getOriginalUrl(), "Original url should be same");
    }

    @Test
    public void testGetOriginalUrl_ExpiredUrl_Failure(){
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto();
        shortenUrlRequestDto.setOriginalUrl("https://www.google.com");
        shortenUrlRequestDto.setUserId(freeUser.getId());
        ShortenUrlResponseDto shortenUrlResponseDto = urlController.shortenUrl(shortenUrlRequestDto);
        String shortUrl = shortenUrlResponseDto.getShortUrl();

        ShortenedUrl shortenedUrl = shortenedUrlRepository.findByShortUrl(shortUrl).get();
        shortenedUrl.setExpiresAt(getCurrentTime() - 87400);
        shortenedUrlRepository.save(shortenedUrl);

        ResolveShortenUrlRequestDto resolveShortenUrlRequestDto = new ResolveShortenUrlRequestDto();
        resolveShortenUrlRequestDto.setShortenUrl(shortUrl);
        ResolveShortenUrlResponseDto responseDto = urlController.resolveShortenedUrl(resolveShortenUrlRequestDto);
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus(), "Status should be success");
        assertNull( responseDto.getOriginalUrl(), "Original url should be null");

    }
}
