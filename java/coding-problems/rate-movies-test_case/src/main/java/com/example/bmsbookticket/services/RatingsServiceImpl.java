package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.MovieNotFoundException;
import com.example.bmsbookticket.exceptions.UserNotFoundException;
import com.example.bmsbookticket.models.Movie;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.MovieRepository;
import com.example.bmsbookticket.repositories.RatingRepository;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingsServiceImpl implements RatingsService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public RatingsServiceImpl(UserRepository userRepository, MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        // Check if user has already rated the movie, then update the rating
        Optional<Rating> optionalRating = this.ratingRepository.findByUserAndMovie(user, movie);
        if(optionalRating.isPresent()){
            Rating ratingObj = optionalRating.get();
            ratingObj.setRating(rating);
            return this.ratingRepository.save(ratingObj);
        }

        Rating ratingObj = new Rating();
        ratingObj.setMovie(movie);
        ratingObj.setUser(user);
        ratingObj.setRating(rating);
        return this.ratingRepository.save(ratingObj);
    }

    @Override
    public double getAverageRating(int movieId) throws MovieNotFoundException {
        this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        return this.ratingRepository.getAverageRatingForMovie(movieId);
    }
}
