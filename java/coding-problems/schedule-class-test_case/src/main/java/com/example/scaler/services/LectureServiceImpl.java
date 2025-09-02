package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidBatchException;
import com.example.scaler.exceptions.InvalidInstructorException;
import com.example.scaler.exceptions.InvalidLectureException;
import com.example.scaler.models.Batch;
import com.example.scaler.models.Instructor;
import com.example.scaler.models.Lecture;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import com.example.scaler.utils.DateUtils;
import com.example.scaler.utils.DronaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService{

    private LectureRepository lectureRepository;
    private InstructorRepository instructorRepository;
    private BatchRepository batchRepository;
    private ScheduledLectureRepository scheduledLectureRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository, InstructorRepository instructorRepository, BatchRepository batchRepository, ScheduledLectureRepository scheduledLectureRepository) {
        this.lectureRepository = lectureRepository;
        this.instructorRepository = instructorRepository;
        this.batchRepository = batchRepository;
        this.scheduledLectureRepository = scheduledLectureRepository;
    }

    @Override
    public List<ScheduledLecture> scheduleLectures(List<Long> lectureIds, Long instructorId, Long batchId) throws InvalidLectureException, InvalidInstructorException, InvalidBatchException {

        List<Lecture> lectures = lectureRepository.findAllById(lectureIds);
        if(lectures.isEmpty()){
            throw  new InvalidLectureException("No lecture found");
        }
        if(lectures.size() != lectureIds.size()){
            throw new InvalidLectureException("Some lectures are not found");
        }
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new InvalidInstructorException("Instructor not found"));

        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new InvalidBatchException("Batch not found"));

        ScheduledLecture lastScheduledLecture = this.scheduledLectureRepository.findLastScheduledLectureByBatchId(batchId);

        List<Pair<Date, Date>> dates = DateUtils.generateDatesAsPerScheduleFromLastDate(lastScheduledLecture.getLectureStartTime(), batch.getSchedule(), lectures.size());

        List<ScheduledLecture> scheduledLectures = new ArrayList<>();
        for(int i = 0; i < lectures.size(); i++){
            ScheduledLecture scheduledLecture = new ScheduledLecture();
            scheduledLecture.setLecture(lectures.get(i));
            scheduledLecture.setBatch(batch);
            scheduledLecture.setInstructor(instructor);
            scheduledLecture.setLectureStartTime(dates.get(i).getFirst());
            scheduledLecture.setLectureEndTime(dates.get(i).getSecond());
            scheduledLecture.setLectureLink(DronaUtils.generateUniqueLectureLink());
            scheduledLectures.add(scheduledLecture);
        }
        return scheduledLectureRepository.saveAll(scheduledLectures);
    }
}
