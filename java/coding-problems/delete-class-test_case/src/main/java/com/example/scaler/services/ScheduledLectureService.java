package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidScheduledLectureException;
import com.example.scaler.models.ScheduledLecture;

import java.util.List;

public interface ScheduledLectureService {

    public List<ScheduledLecture> rescheduleScheduledLecture(long scheduledLectureId) throws InvalidScheduledLectureException;

}
