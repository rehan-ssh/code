package com.example.scaler.controllers;

import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.dtos.ScheduleLectureRequestDto;
import com.example.scaler.dtos.ScheduleLecturesResponseDto;
import com.example.scaler.models.*;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import com.example.scaler.utils.DronaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestLectureController {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private ScheduledLectureRepository scheduledLectureRepository;

    @Autowired
    private LectureController lectureController;

    private List<Long> lectureIds = new ArrayList<>();
    private Batch batch;

    private Instructor instructor;

    @BeforeEach
    public void setup(){
        scheduledLectureRepository.deleteAll();
        lectureRepository.deleteAll();
        batchRepository.deleteAll();
        instructorRepository.deleteAll();


        batch = new Batch();
        batch.setName("Batch 1");
        batch.setSchedule(Schedule.MWF_MORNING);
        batch = batchRepository.save(batch);


        instructor = new Instructor();
        instructor.setName("Instructor 1");
        instructor = instructorRepository.save(instructor);


        Lecture lecture_1 = new Lecture();
        lecture_1.setName("SQL: Schema Design - 1");
        lecture_1.setDescription("SQL: Schema Design - 1");
        lecture_1 = lectureRepository.save(lecture_1);

        Lecture lecture_0 = new Lecture();
        lecture_0.setName("SQL: Schema Design - 2");
        lecture_0.setDescription("SQL: Schema Design - 2");
        lecture_0 = lectureRepository.save(lecture_0);

        Lecture lecture1 = new Lecture();
        lecture1.setName("Backend LLD: OOP-1: Intro to OOP");
        lecture1.setDescription("Backend LLD: OOP-1: Intro to OOP");
        lecture1 = lectureRepository.save(lecture1);
        lectureIds.add(lecture1.getId());

        Lecture lecture2 = new Lecture();
        lecture2.setName("Backend LLD: OOP-2: Access Modifiers and Constructors");
        lecture2.setDescription("Backend LLD: OOP-2: Access Modifiers and Constructors");
        lecture2 = lectureRepository.save(lecture2);
        lectureIds.add(lecture2.getId());

        Lecture lecture3 = new Lecture();
        lecture3.setName("Backend LLD: OOP-3: Inheritance");
        lecture3.setDescription("Backend LLD: OOP-3: Inheritance");
        lecture3 = lectureRepository.save(lecture3);
        lectureIds.add(lecture3.getId());

        Lecture lecture4 = new Lecture();
        lecture4.setName("Backend LLD: OOP-4: Polymorphism");
        lecture4.setDescription("Backend LLD: OOP-4: Polymorphism");
        lecture4 = lectureRepository.save(lecture4);
        lectureIds.add(lecture4.getId());


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 6);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        calendar.add(Calendar.MINUTE, 30);
        Date endDate = calendar.getTime();


        ScheduledLecture scheduledLecture1 = new ScheduledLecture();
        scheduledLecture1.setBatch(batch);
        scheduledLecture1.setInstructor(instructor);
        scheduledLecture1.setLecture(lecture_1);
        scheduledLecture1.setLectureLink(DronaUtils.generateUniqueLectureLink());
        scheduledLecture1.setLectureStartTime(startDate);
        scheduledLecture1.setLectureEndTime(endDate);
        scheduledLectureRepository.save(scheduledLecture1);

        calendar.add(Calendar.DATE, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startDate = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        calendar.add(Calendar.MINUTE, 30);
        endDate = calendar.getTime();

        ScheduledLecture scheduledLecture2 = new ScheduledLecture();
        scheduledLecture2.setBatch(batch);
        scheduledLecture2.setInstructor(instructor);
        scheduledLecture2.setLecture(lecture_0);
        scheduledLecture2.setLectureLink(DronaUtils.generateUniqueLectureLink());
        scheduledLecture2.setLectureStartTime(startDate);
        scheduledLecture2.setLectureEndTime(endDate);
        scheduledLectureRepository.save(scheduledLecture2);
    }

    @Test
    public void testScheduleLecture_Success(){
        ScheduleLectureRequestDto requestDto = new ScheduleLectureRequestDto();
        requestDto.setBatchId(batch.getId());
        requestDto.setInstructorId(instructor.getId());
        requestDto.setLectureIds(lectureIds);

        ScheduleLecturesResponseDto responseDto = lectureController.scheduleLectures(requestDto);

        assertNotNull(responseDto, "Response should not be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.SUCCESS);
        assertNotNull(responseDto.getScheduledLectures(), "Scheduled lectures should not be null");

        List<ScheduledLecture> scheduledLectures = responseDto.getScheduledLectures();
        assertEquals(scheduledLectures.size(), lectureIds.size(), "Scheduled lectures size should be equal to lecture ids size");
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < scheduledLectures.size(); i++){
            ScheduledLecture scheduledLecture = scheduledLectures.get(i);
            assertEquals(scheduledLecture.getBatch().getId(), batch.getId(), "Lecture should be scheduled for the same batch as the request batch id");
            assertEquals(scheduledLecture.getInstructor().getId(), instructor.getId(), "Instructor should be the same as the request instructor id");
            assertEquals(scheduledLecture.getLecture().getId(), lectureIds.get(i), "Lecture id should be equal");
            assertNotNull(scheduledLecture.getLectureLink(), "Lecture link should not be null");
            assertNotNull(scheduledLecture.getLectureStartTime(), "Lecture start time should not be null");
            assertNotNull(scheduledLecture.getLectureEndTime(), "Lecture end time should not be null");
            calendar.setTime(scheduledLecture.getLectureStartTime());
            assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 7, "Lecture start time hour should be 7");
            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {

            } else {
                throw new AssertionError("Lecture should be scheduled for Monday, Wednesday or Friday only");
            }
        }
    }

    @Test
    public void testScheduleLecture_InvalidLectureIds_1_Failure(){
        ScheduleLectureRequestDto requestDto = new ScheduleLectureRequestDto();
        requestDto.setBatchId(batch.getId());
        requestDto.setInstructorId(instructor.getId());
        requestDto.setLectureIds(List.of(100000L, 1000001L));

        ScheduleLecturesResponseDto responseDto = lectureController.scheduleLectures(requestDto);

        assertNotNull(responseDto, "Response should not be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
    }


    @Test
    public void testScheduleLecture_InvalidLectureIds_2_Failure(){
        ScheduleLectureRequestDto requestDto = new ScheduleLectureRequestDto();
        requestDto.setBatchId(batch.getId());
        requestDto.setInstructorId(instructor.getId());
        lectureIds.add(100000L);
        requestDto.setLectureIds(lectureIds);

        ScheduleLecturesResponseDto responseDto = lectureController.scheduleLectures(requestDto);

        assertNotNull(responseDto, "Response should not be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
    }

    @Test
    public void testScheduleLecture_InvalidBatchId_Failure(){
        ScheduleLectureRequestDto requestDto = new ScheduleLectureRequestDto();
        requestDto.setBatchId(10000L);
        requestDto.setInstructorId(instructor.getId());
        requestDto.setLectureIds(lectureIds);

        ScheduleLecturesResponseDto responseDto = lectureController.scheduleLectures(requestDto);

        assertNotNull(responseDto, "Response should not be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
    }

    @Test
    public void testScheduleLecture_InvalidInstructorId_Failure(){
        ScheduleLectureRequestDto requestDto = new ScheduleLectureRequestDto();
        requestDto.setBatchId(batch.getId());
        requestDto.setInstructorId(10000L);
        requestDto.setLectureIds(lectureIds);

        ScheduleLecturesResponseDto responseDto = lectureController.scheduleLectures(requestDto);

        assertNotNull(responseDto, "Response should not be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be failure");
    }
}
