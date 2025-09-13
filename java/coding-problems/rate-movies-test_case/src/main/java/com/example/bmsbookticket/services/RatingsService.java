package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.MovieNotFoundException;
import com.example.bmsbookticket.exceptions.UserNotFoundException;
import com.example.bmsbookticket.models.Rating;

public interface RatingsService {

    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException;

    public double getAverageRating(int movieId) throws MovieNotFoundException;
}
