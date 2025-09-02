package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Movie;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movie.id = ?1")
    Double getAverageRatingForMovie(Integer movieId);


    Optional<Rating> findByUserAndMovie(User user, Movie movie);
}
