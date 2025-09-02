package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
}
