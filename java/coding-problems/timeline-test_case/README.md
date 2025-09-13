# Implement functionality which will render the 'All classes' page for learners for Scaler Academy

## Problem Statement

You are building web application for Scaler Academy. As a part of this system, you need to implement a functionality will render the 'All classes' page for learners. This page will contain a list of all the lectures that a learner has access to.

## Requirements

* This functionality will be called as soon as the learner logs in to the system to render the 'All classes' page.
* The learner's id will be the input to this functionality.
* The output of this functionality will be a list of all the scheduled lectures that the learner has access to.
* A learner can move around batches using the course pause and resume functionality. This means that a learner can be a part of multiple previous batches.
* A learner's batch related information is tracked via 'batch_learner' table (BatchLearner entity). This table has entry and exit dates fields which represent the time period for which the learner was a part of the batch. A null exit date means that the learner is still a part of the batch.
* Thus, a learner can have multiple previous batches, but at max 1 current batch.
* A learner should be shown only those lectures which are scheduled during the time period when the learner was a part of the batch. 
  * Eg #1. If a learner was a part of batch 1 from 1st Jan 2021 to 31st Jan 2021, then the learner should be shown only those lectures which are scheduled between 1st Jan 2021 to 31st Jan 2021.
  * Eg #2. If a learner was a part of batch 1 from 1st Jan 2021 to 31st Jan 2021, and then batch 2 from 1st Feb 2021 to 28th Feb 2021, then the learner should be able to see all the lectures that happened for batch 1 between 1st Jan and 31st Jan, and all the lectures that happened for batch 2 between 1st Feb and 28th Feb. No other lectures should be shown to the learner.
  * Eg #3. If a learner was a part of batch 1 from 1st Jan 2021 to 31st Jan 2021, and then batch 2 from 1st Feb 2021 onwards i.e. learner is still a part of batch 2, then the learner should be able to see all the lectures that happened for batch 1 between 1st Jan and 31st Jan, and all the lectures that happened for batch 2 from 1st Feb onwards. No other lectures should be shown to the learner.
* The output should be sorted by the lecture start time in ascending order.


## Instructions
1. Carefully look at the dto package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `fetchTimeline` method inside the `LearnerController`.
4. Implement the `LectureService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.