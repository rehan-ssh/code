package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.SeatTypeShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeShowRepository extends JpaRepository<SeatTypeShow, Integer> {

}
