package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidScheduledLectureException;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.ScheduledLectureRepository;
import com.example.scaler.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduledLectureServiceImpl implements ScheduledLectureService {

    private ScheduledLectureRepository scheduledLectureRepository;

    @Autowired
    public ScheduledLectureServiceImpl(ScheduledLectureRepository scheduledLectureRepository) {
        this.scheduledLectureRepository = scheduledLectureRepository;
    }


    @Override
    public List<ScheduledLecture> rescheduleScheduledLecture(long scheduledLectureId) throws InvalidScheduledLectureException {
        ScheduledLecture scheduledLecture = this.scheduledLectureRepository.findById(scheduledLectureId).orElseThrow(()->new InvalidScheduledLectureException("Scheduled lecture not found"));
        // Find all the lectures after this lecture
        List<ScheduledLecture> scheduledLectures = this.scheduledLectureRepository.findAllByBatchIdAndLectureStartTimeAfter(scheduledLecture.getBatch().getId(), scheduledLecture.getLectureStartTime());

        scheduledLectures.add(0, scheduledLecture);

        // Move all the lectures after this lecture by 1 lecture
        List<Pair<Date, Date>> dates = DateUtils.generateDatesAsPerScheduleFromLastDate(scheduledLecture.getLectureStartTime(), scheduledLecture.getBatch().getSchedule(), scheduledLectures.size() + 1);

        for(int i = 0; i < scheduledLectures.size(); i++) {
            scheduledLectures.get(i).setLectureStartTime(dates.get(i).getFirst());
            scheduledLectures.get(i).setLectureEndTime(dates.get(i).getSecond());
            ScheduledLecture scheduledLecture1 = this.scheduledLectureRepository.save(scheduledLectures.get(i));
            scheduledLectures.set(i, scheduledLecture1);
        }
        return scheduledLectures;
    }
}
