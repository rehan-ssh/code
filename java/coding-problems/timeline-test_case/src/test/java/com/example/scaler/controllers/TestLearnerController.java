package com.example.scaler.controllers;

import com.example.scaler.dtos.FetchTimelineRequestDto;
import com.example.scaler.dtos.FetchTimelineResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestLearnerController {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private BatchLearnerRepository batchLearnerRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ScheduledLectureRepository scheduledLectureRepository;

    @Autowired
    private LearnerController learnerController;

    @BeforeEach
    public void setup(){
        scheduledLectureRepository.deleteAll();
        batchLearnerRepository.deleteAll();
        lectureRepository.deleteAll();
        learnerRepository.deleteAll();
        batchRepository.deleteAll();
    }

    @Test
    public void testFetchTimeline_LearnerHas2PrevBatchesWith2LecturesInEachAndNoCurrentBatch(){

        List<Lecture> lectures = new ArrayList<>();
        for(int i=0; i<9; i++){
            Lecture lecture = new Lecture();
            lecture.setName("Lecture "+i);
            lecture = lectureRepository.save(lecture);
            lectures.add(lecture);
        }

        List<Batch> batches = new ArrayList<>();
        for(int i=0; i<3; i++){
            Batch batch = new Batch();
            batch.setName("Batch "+i);
            batch.setSchedule(Schedule.MWF_EVENING);
            batch = batchRepository.save(batch);
            batches.add(batch);
        }

        List<ScheduledLecture> scheduledLectures = new ArrayList<>();
        Date startDate = new Date(2024, Calendar.JANUARY, 1, 21,0,0);
        for(int i=0; i<9; i++){
            ScheduledLecture scheduledLecture = new ScheduledLecture();
            scheduledLecture.setLecture(lectures.get(i));
            scheduledLecture.setBatch(batches.get(i/3));
            scheduledLecture.setLectureStartTime(startDate);
            Date endDate = new Date(startDate.getTime() + 2*60*60*1000);
            scheduledLecture.setLectureEndTime(endDate);
            scheduledLecture.setLectureLink("Link "+i);
            scheduledLecture = scheduledLectureRepository.save(scheduledLecture);
            scheduledLectures.add(scheduledLecture);
            //Move start ahead date by 2 days
            startDate = new Date(startDate.getTime() + 2*24*60*60*1000);
        }

        Learner learner = new Learner();
        learner.setName("Learner 1");
        learner = learnerRepository.save(learner);


        BatchLearner batchLearner1 = new BatchLearner();
        batchLearner1.setBatch(batches.get(0));
        batchLearner1.setLearner(learner);
        batchLearner1.setEntryDate(new Date(2023, Calendar.DECEMBER, 31));
        batchLearner1.setExitDate(new Date(2024, Calendar.JANUARY, 4));
        batchLearner1 = batchLearnerRepository.save(batchLearner1);

        BatchLearner batchLearner2 = new BatchLearner();
        batchLearner2.setBatch(batches.get(1));
        batchLearner2.setLearner(learner);
        batchLearner2.setEntryDate(new Date(2024, Calendar.JANUARY, 8));
        batchLearner2.setExitDate(new Date(2024, Calendar.JANUARY, 12));
        batchLearner2 = batchLearnerRepository.save(batchLearner2);


        FetchTimelineRequestDto requestDto = new FetchTimelineRequestDto();
        requestDto.setLearnerId(learner.getId());
        FetchTimelineResponseDto fetchTimelineResponseDto = learnerController.fetchTimeline(requestDto);
        assertNotNull(fetchTimelineResponseDto, "Response cannot be null");
        assertEquals(ResponseStatus.SUCCESS, fetchTimelineResponseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(fetchTimelineResponseDto.getLectures(), "Lectures cannot be null");
        assertEquals(4, fetchTimelineResponseDto.getLectures().size(), "There should be 4 lectures in the response");
    }

    @Test
    public void testFetchTimeline_LearnerHas2PrevBatchesWith2LecturesInEachAnd3LectureInCurrentBatch(){

        List<Lecture> lectures = new ArrayList<>();
        for(int i=0; i<9; i++){
            Lecture lecture = new Lecture();
            lecture.setName("Lecture "+i);
            lecture = lectureRepository.save(lecture);
            lectures.add(lecture);
        }

        List<Batch> batches = new ArrayList<>();
        for(int i=0; i<3; i++){
            Batch batch = new Batch();
            batch.setName("Batch "+i);
            batch.setSchedule(Schedule.MWF_EVENING);
            batch = batchRepository.save(batch);
            batches.add(batch);
        }

        List<ScheduledLecture> scheduledLectures = new ArrayList<>();
        Date startDate = new Date(2024, Calendar.JANUARY, 1, 21,0,0);
        for(int i=0; i<9; i++){
            ScheduledLecture scheduledLecture = new ScheduledLecture();
            scheduledLecture.setLecture(lectures.get(i));
            scheduledLecture.setBatch(batches.get(i/3));
            scheduledLecture.setLectureStartTime(startDate);
            Date endDate = new Date(startDate.getTime() + 2*60*60*1000);
            scheduledLecture.setLectureEndTime(endDate);
            scheduledLecture.setLectureLink("Link "+i);
            scheduledLecture = scheduledLectureRepository.save(scheduledLecture);
            scheduledLectures.add(scheduledLecture);
            //Move start ahead date by 2 days
            startDate = new Date(startDate.getTime() + 2*24*60*60*1000);
        }

        Learner learner = new Learner();
        learner.setName("Learner 1");
        learner = learnerRepository.save(learner);



        BatchLearner batchLearner1 = new BatchLearner();
        batchLearner1.setBatch(batches.get(0));
        batchLearner1.setLearner(learner);
        batchLearner1.setEntryDate(new Date(2023, Calendar.DECEMBER, 31));
        batchLearner1.setExitDate(new Date(2024, Calendar.JANUARY, 4));
        batchLearner1 = batchLearnerRepository.save(batchLearner1);

        BatchLearner batchLearner2 = new BatchLearner();
        batchLearner2.setBatch(batches.get(1));
        batchLearner2.setLearner(learner);
        batchLearner2.setEntryDate(new Date(2024, Calendar.JANUARY, 8));
        batchLearner2.setExitDate(new Date(2024, Calendar.JANUARY, 12));
        batchLearner2 = batchLearnerRepository.save(batchLearner2);

        BatchLearner batchLearner3 = new BatchLearner();
        batchLearner3.setBatch(batches.get(2));
        batchLearner3.setLearner(learner);
        batchLearner3.setEntryDate(new Date(2024, Calendar.JANUARY, 13, 9,0,0));
        batchLearner3 = batchLearnerRepository.save(batchLearner3);

        FetchTimelineRequestDto requestDto = new FetchTimelineRequestDto();
        requestDto.setLearnerId(learner.getId());
        FetchTimelineResponseDto fetchTimelineResponseDto = learnerController.fetchTimeline(requestDto);
        assertNotNull(fetchTimelineResponseDto, "Response cannot be null");
        assertEquals(ResponseStatus.SUCCESS, fetchTimelineResponseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(fetchTimelineResponseDto.getLectures(), "Lectures cannot be null");
        assertEquals(7, fetchTimelineResponseDto.getLectures().size(), "There should be 7 lectures in the response");
    }

    @Test
    public void testFetchTimeline_LearnerHarNeverChangedABatch(){

        List<Lecture> lectures = new ArrayList<>();
        for(int i=0; i<9; i++){
            Lecture lecture = new Lecture();
            lecture.setName("Lecture "+i);
            lecture = lectureRepository.save(lecture);
            lectures.add(lecture);
        }

        List<Batch> batches = new ArrayList<>();
        for(int i=0; i<3; i++){
            Batch batch = new Batch();
            batch.setName("Batch "+i);
            batch.setSchedule(Schedule.MWF_EVENING);
            batch = batchRepository.save(batch);
            batches.add(batch);
        }

        List<ScheduledLecture> scheduledLectures = new ArrayList<>();
        Date startDate = new Date(2024, Calendar.JANUARY, 1, 21,0,0);
        for(int i=0; i<9; i++){
            ScheduledLecture scheduledLecture = new ScheduledLecture();
            scheduledLecture.setLecture(lectures.get(i));
            scheduledLecture.setBatch(batches.get(i/3));
            scheduledLecture.setLectureStartTime(startDate);
            Date endDate = new Date(startDate.getTime() + 2*60*60*1000);
            scheduledLecture.setLectureEndTime(endDate);
            scheduledLecture.setLectureLink("Link "+i);
            scheduledLecture = scheduledLectureRepository.save(scheduledLecture);
            scheduledLectures.add(scheduledLecture);
            //Move start ahead date by 2 days
            startDate = new Date(startDate.getTime() + 2*24*60*60*1000);
        }

        Learner learner = new Learner();
        learner.setName("Learner 1");
        learner = learnerRepository.save(learner);

        BatchLearner batchLearner1 = new BatchLearner();
        batchLearner1.setBatch(batches.get(0));
        batchLearner1.setLearner(learner);
        batchLearner1.setEntryDate(new Date(2023, Calendar.DECEMBER, 31));
        batchLearner1 = batchLearnerRepository.save(batchLearner1);

        FetchTimelineRequestDto requestDto = new FetchTimelineRequestDto();
        requestDto.setLearnerId(learner.getId());
        FetchTimelineResponseDto fetchTimelineResponseDto = learnerController.fetchTimeline(requestDto);
        assertNotNull(fetchTimelineResponseDto, "Response cannot be null");
        assertEquals(ResponseStatus.SUCCESS, fetchTimelineResponseDto.getResponseStatus(), "Response status should be success");
        assertNotNull(fetchTimelineResponseDto.getLectures(), "Lectures cannot be null");
        assertEquals(3, fetchTimelineResponseDto.getLectures().size(), "There should be 3 lectures in the response");
    }

    @Test
    public void testFetchTimeline_InvalidLearnerId(){
        FetchTimelineRequestDto requestDto = new FetchTimelineRequestDto();
        requestDto.setLearnerId(100);
        FetchTimelineResponseDto fetchTimelineResponseDto = learnerController.fetchTimeline(requestDto);
        assertNotNull(fetchTimelineResponseDto, "Response cannot be null");
        assertEquals(ResponseStatus.FAILURE, fetchTimelineResponseDto.getResponseStatus(), "Response status should be failure");
        assertNull(fetchTimelineResponseDto.getLectures(), "Lectures should be null");
    }


}
