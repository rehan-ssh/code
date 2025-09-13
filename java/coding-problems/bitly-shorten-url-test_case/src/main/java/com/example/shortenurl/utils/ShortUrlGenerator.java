package com.example.shortenurl.utils;

import java.util.Random;

public class ShortUrlGenerator {

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    public static String generateShortUrl() {
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(ALLOWED_CHARACTERS.length());
            shortUrl.append(ALLOWED_CHARACTERS.charAt(index));
        }

        return shortUrl.toString();
    }
}
