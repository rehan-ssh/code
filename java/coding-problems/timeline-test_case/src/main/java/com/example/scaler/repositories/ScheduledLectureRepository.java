package com.example.scaler.repositories;

import com.example.scaler.models.ScheduledLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduledLectureRepository extends JpaRepository<ScheduledLecture, Long> {

    List<ScheduledLecture> findByBatchIdAndLectureStartTimeAfterAndLectureEndTimeBeforeOrderByLectureStartTime(Long batchId, Date startTime, Date endTime);

    List<ScheduledLecture> findByBatchIdAndLectureStartTimeAfterOrderByLectureStartTime(Long batchId, Date startTime);

}
