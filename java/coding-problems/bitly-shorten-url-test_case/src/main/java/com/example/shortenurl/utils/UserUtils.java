package com.example.shortenurl.utils;

import com.example.shortenurl.models.UserPlan;

public class UserUtils {

    public static long getExpiryTimeByUserPlan(UserPlan userPlan){
        long currTime = System.currentTimeMillis();
        long currTimeInSec = currTime / 1000;
        return switch (userPlan) {
            case FREE -> currTimeInSec + 86400;
            case TEAM -> currTimeInSec + 86400 * 7;
            case BUSINESS -> currTimeInSec + 86400 * 30;
            case ENTERPRISE -> currTimeInSec + 86400 * 365;
        };

    }
}
