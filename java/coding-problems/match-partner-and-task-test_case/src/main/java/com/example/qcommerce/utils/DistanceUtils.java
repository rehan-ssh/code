package com.example.qcommerce.utils;

import com.example.qcommerce.models.Location;

public class DistanceUtils {

    public static double calculateDistance(Location location1, Location location2){
        return Math.sqrt(Math.pow(location1.getLatitude() - location2.getLatitude(), 2) + Math.pow(location1.getLongitude() - location2.getLongitude(), 2));
    }
}
