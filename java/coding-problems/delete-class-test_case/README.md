# Implement functionality schedule lectures functionality for Scaler Academy

## Problem Statement

You are building web application for Scaler Academy. As a part of this system, you need to implement a functionality which will be used to reschedule classes for a batch.

## Requirements

* You will be given an id of a scheduled lecture.
* You need update the start and end time of the given scheduled lecture to next class day as per the schedule of the batch and make sure that all the further classes are also rescheduled accordingly.
* Eg. If a batch is on TTS Morning schedule and if we want to reschedule a lecture which is scheduled for Tuesday, then this lecture should be moved to Thursday and all the further lectures should also be rescheduled accordingly.
* You need to return list of all the rescheduled lectures.



## Instructions
1. Carefully look at the dto package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `rescheduleScheduledLecture` method inside the `LectureController`.
4. Implement the `ScheduledLectureService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.