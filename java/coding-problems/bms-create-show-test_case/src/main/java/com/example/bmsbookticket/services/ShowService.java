package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.*;
import com.example.bmsbookticket.models.Feature;
import com.example.bmsbookticket.models.SeatType;
import com.example.bmsbookticket.models.Show;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

public interface ShowService {
    public Show createShow(int userId, int movieId, int screenId, Date startTime, Date endTime, List<Pair<SeatType, Double>> pricingConfig, List<Feature> features) throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException, UserNotFoundException, UnAuthorizedAccessException;
}
