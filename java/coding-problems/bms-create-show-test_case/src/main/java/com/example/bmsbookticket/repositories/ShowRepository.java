package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Override
    Optional<Show> findById(Integer showId);
}
