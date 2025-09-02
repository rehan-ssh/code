package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Integer> {

    @Override
    List<Seat> findAllById(Iterable<Integer> integers);
}
