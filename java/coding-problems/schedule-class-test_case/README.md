# Implement functionality schedule lectures functionality for Scaler Academy

## Problem Statement

You are building web application for Scaler Academy. As a part of this system, you need to implement a functionality which will be used to schedule classes for a batch.

## Requirements

* You will be given a list of lectures, a batch, a instructor.
* Depending upon the schedule of the batch, we need to schedule the lectures for the batch to be conducted by the instructor.
* A batch will have 4 types of schedules and fixed start and end times for the lectures:
  * MWF_MORNING: Monday, Wednesday, Friday at 7 AM to 9.30 AM
  * MWF_EVENING: Monday, Wednesday, Friday at 9 PM to 11.30 PM
  * TTS_MORNING: Tuesday, Thursday, Saturday at 7 AM to 9.30 AM
  * TTS_EVENING: Tuesday, Thursday, Saturday at 9 PM to 11.30 PM
* Now for a given batch id, figure out their last lecture date and assign the next set of lectures based on the schedule.
* Store the scheduled lectures related data in the `scheduled_classes` table (ScheduledClass entity).
* Every scheduled lecture must have a unique link which will be used by the students to join the class.



## Instructions
1. Carefully look at the dto package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `scheduleLectures` method inside the `LectureController`.
4. Implement the `LectureService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.