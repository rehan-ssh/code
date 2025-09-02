package com.example.scaler.utils;

import com.example.scaler.models.Schedule;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

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
}
