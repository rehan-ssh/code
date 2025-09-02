package com.example.ecom.adapters;

import com.example.ecom.libraries.GoogleMapsApi;
import com.example.ecom.libraries.models.GLocation;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsAdapter implements MapsAdapter{

    private final GoogleMapsApi googleMapsApi = new GoogleMapsApi();

    @Override
    public int getEstimatedTime(double srcLat, double srcLong, double destLat, double destLong) {
        GLocation src = new GLocation();
        src.setLatitude(srcLat);
        src.setLongitude(srcLong);
        GLocation dest = new GLocation();
        dest.setLatitude(destLat);
        dest.setLongitude(destLong);
        return googleMapsApi.estimate(src, dest);
    }
}
