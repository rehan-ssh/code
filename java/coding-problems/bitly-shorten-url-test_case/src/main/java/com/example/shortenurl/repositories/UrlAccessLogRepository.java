package com.example.shortenurl.repositories;

import com.example.shortenurl.models.UrlAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlAccessLogRepository extends JpaRepository<UrlAccessLog, Long> {
}
