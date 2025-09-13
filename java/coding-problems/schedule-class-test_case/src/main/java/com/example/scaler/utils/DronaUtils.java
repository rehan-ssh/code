package com.example.scaler.utils;

import java.util.UUID;

public class DronaUtils {

    public static String generateUniqueLectureLink(){
        return "https://scaler.com/lecture/" + UUID.randomUUID().toString();
    }
}
