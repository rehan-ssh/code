package com.example.scaler.controllers;

import com.example.scaler.dtos.RescheduleScheduledLectureRequestDto;
import com.example.scaler.dtos.RescheduleScheduledLectureResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.*;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestScheduledLectureController {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private ScheduledLectureRepository scheduledLectureRepository;

    @Autowired
    private ScheduledLectureController lectureController;

    private List<Long> lectureIds = new ArrayList<>();
    private Batch batch;

    private Instructor instructor;
    private List<ScheduledLecture> scheduledLectures = new ArrayList<>();

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

        List<Pair<Date, Date>> dates = generateDatesAsPerScheduleFromLastDate(calendar.getTime(), batch.getSchedule(), 6);



        ScheduledLecture scheduledLecture1 = new ScheduledLecture();
        scheduledLecture1.setBatch(batch);
        scheduledLecture1.setInstructor(instructor);
        scheduledLecture1.setLecture(lecture_1);
        scheduledLecture1.setLectureStartTime(dates.get(0).getFirst());
        scheduledLecture1.setLectureEndTime(dates.get(0).getSecond());
        scheduledLecture1 = scheduledLectureRepository.save(scheduledLecture1);


        ScheduledLecture scheduledLecture2 = new ScheduledLecture();
        scheduledLecture2.setBatch(batch);
        scheduledLecture2.setInstructor(instructor);
        scheduledLecture2.setLecture(lecture_0);
        scheduledLecture2.setLectureStartTime(dates.get(1).getFirst());
        scheduledLecture2.setLectureEndTime(dates.get(1).getSecond());
        scheduledLecture2 = scheduledLectureRepository.save(scheduledLecture2);

        ScheduledLecture scheduledLecture3 = new ScheduledLecture();
        scheduledLecture3.setBatch(batch);
        scheduledLecture3.setInstructor(instructor);
        scheduledLecture3.setLecture(lecture1);
        scheduledLecture3.setLectureStartTime(dates.get(2).getFirst());
        scheduledLecture3.setLectureEndTime(dates.get(2).getSecond());
        scheduledLecture3 = scheduledLectureRepository.save(scheduledLecture3);

        ScheduledLecture scheduledLecture4 = new ScheduledLecture();
        scheduledLecture4.setBatch(batch);
        scheduledLecture4.setInstructor(instructor);
        scheduledLecture4.setLecture(lecture2);
        scheduledLecture4.setLectureStartTime(dates.get(3).getFirst());
        scheduledLecture4.setLectureEndTime(dates.get(3).getSecond());
        scheduledLecture4 = scheduledLectureRepository.save(scheduledLecture4);

        ScheduledLecture scheduledLecture5 = new ScheduledLecture();
        scheduledLecture5.setBatch(batch);
        scheduledLecture5.setInstructor(instructor);
        scheduledLecture5.setLecture(lecture3);
        scheduledLecture5.setLectureStartTime(dates.get(4).getFirst());
        scheduledLecture5.setLectureEndTime(dates.get(4).getSecond());
        scheduledLecture5 = scheduledLectureRepository.save(scheduledLecture5);

        ScheduledLecture scheduledLecture6 = new ScheduledLecture();
        scheduledLecture6.setBatch(batch);
        scheduledLecture6.setInstructor(instructor);
        scheduledLecture6.setLecture(lecture4);
        scheduledLecture6.setLectureStartTime(dates.get(5).getFirst());
        scheduledLecture6.setLectureEndTime(dates.get(5).getSecond());
        scheduledLecture6 = scheduledLectureRepository.save(scheduledLecture6);

        scheduledLectures = List.of(scheduledLecture1, scheduledLecture2, scheduledLecture3, scheduledLecture4, scheduledLecture5, scheduledLecture6);
    }

    public static List<Pair<Date, Date>> generateDatesAsPerScheduleFromLastDate(Date lastDate, Schedule schedule, int num) {
        List<Pair<Date, Date>> dates = new ArrayList<>();
        Date firstDayStartTime = getFirstDateFromLastDateAndSchedule(lastDate, schedule);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayStartTime);
        Calendar firstDayEndTime = (Calendar) calendar.clone();
        firstDayEndTime.add(Calendar.HOUR_OF_DAY, 2);
        firstDayEndTime.add(Calendar.MINUTE, 30);
        dates.add(Pair.of(firstDayStartTime, firstDayEndTime.getTime()));
        for(int i = 1; i < num; i++) {
            Date startDateTime = getFirstDateFromLastDateAndSchedule(firstDayStartTime, schedule);
            Date endDateTime = (Date) startDateTime.clone();
            calendar.setTime(endDateTime);
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            calendar.add(Calendar.MINUTE, 30);
            dates.add(Pair.of(startDateTime, calendar.getTime()));
            firstDayStartTime = startDateTime;
        }
        return dates;
    }

    public static Date getFirstDateFromLastDateAndSchedule(Date lastDate, Schedule schedule) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        if (schedule == Schedule.MWF_EVENING || schedule == Schedule.MWF_MORNING) {
            if (dayNum == Calendar.MONDAY || dayNum == Calendar.WEDNESDAY || dayNum == Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, 2);
            } else if (dayNum == Calendar.TUESDAY || dayNum == Calendar.THURSDAY || dayNum == Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, 1);
            } else if (dayNum == Calendar.FRIDAY) {
                calendar.add(Calendar.DATE, 3);
            }
        } else if (schedule == Schedule.TTS_EVENING || schedule == Schedule.TTS_MORNING) {
            if (dayNum == Calendar.MONDAY || dayNum == Calendar.WEDNESDAY || dayNum == Calendar.FRIDAY) {
                calendar.add(Calendar.DATE, 1);
            } else if (dayNum == Calendar.TUESDAY || dayNum == Calendar.THURSDAY || dayNum == Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, 2);
            } else if (dayNum == Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, 3);
            }
        }

        if (schedule == Schedule.MWF_EVENING || schedule == Schedule.TTS_EVENING) {
            calendar.set(Calendar.HOUR_OF_DAY, 21);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else if (schedule == Schedule.MWF_MORNING || schedule == Schedule.TTS_MORNING) {
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        return calendar.getTime();
    }

    @Test
    public void testRescheduleScheduledLecture_Success_1(){
        RescheduleScheduledLectureRequestDto requestDto = new RescheduleScheduledLectureRequestDto();
        requestDto.setScheduledLectureId(scheduledLectures.get(1).getId());
        ScheduledLecture originalLecture = scheduledLectures.get(1);
        List<ScheduledLecture> scheduledLectures = lectureController.rescheduleScheduledLecture(requestDto).getScheduledLectures();
        assertEquals(5, scheduledLectures.size());
        ScheduledLecture modifiedLecture = scheduledLectures.get(0);
        assertTrue(modifiedLecture.getLectureStartTime().after(originalLecture.getLectureStartTime()), "Modified lecture start time should be after original lecture start time");
        assertEquals(13, modifiedLecture.getLectureStartTime().getDate(), "As per schedule, the modified lecture should be on 13th");

    }

    @Test
    public void testRescheduleScheduledLecture_Success_2(){
        RescheduleScheduledLectureRequestDto requestDto = new RescheduleScheduledLectureRequestDto();
        requestDto.setScheduledLectureId(scheduledLectures.get(0).getId());
        ScheduledLecture originalLecture = scheduledLectures.get(0);
        List<ScheduledLecture> scheduledLectures = lectureController.rescheduleScheduledLecture(requestDto).getScheduledLectures();
        assertEquals(6, scheduledLectures.size());
        ScheduledLecture modifiedLecture = scheduledLectures.get(0);
        assertTrue(modifiedLecture.getLectureStartTime().after(originalLecture.getLectureStartTime()), "Modified lecture start time should be after original lecture start time");
        assertEquals(11, modifiedLecture.getLectureStartTime().getDate(), "As per schedule, the modified lecture should be on 11th");

    }

    @Test
    public void testRescheduleScheduledLecture_ScheduledLectureNotFound(){
        RescheduleScheduledLectureRequestDto requestDto = new RescheduleScheduledLectureRequestDto();
        requestDto.setScheduledLectureId(1000L);
        RescheduleScheduledLectureResponseDto rescheduleScheduledLectureResponseDto = lectureController.rescheduleScheduledLecture(requestDto);
        assertEquals(ResponseStatus.FAILURE, rescheduleScheduledLectureResponseDto.getResponseStatus());
        assertNull(rescheduleScheduledLectureResponseDto.getScheduledLectures());
    }





}
