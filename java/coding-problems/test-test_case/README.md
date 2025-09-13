# Implement functionality which will allow learners to give MCQ exams

## Problem Statement

You are building web application for Scaler Academy. This functionality will be used by the learners to give MSQ based exams.

## Requirements

* We need to create 3 functionalities to support this feature.
  1. Start a exam 
  2. Answer questions
  3. Submit a exam

1. Start a exam
* We will get learner id and exam id as input.
* We need to create a new entry in the `learner_exams` table (LearnerExam entity). Set the status as STARTED and capture the start time.
* If the learner has already started the exam, then we should not allow them to start the exam again.
* If the learner has already submitted the exam, then we should not allow them to start the exam again.
* If an entry is already present in the `learner_exams` table for the given learner and exam, then we should not allow them to start the exam again.
* Return the learner exam object in response.

2. Answer questions
* We will get learner id, question id and the option id as input.
* We need to create a new entry in the `learner_question_responses` table (LearnerQuestionResponse entity).
* We should only capture the answer if the learner has started the exam, else we should throw an error.
* We can assume that each question will have multiple options, but only one option will be correct.
* A learner can update their answer as many times as they want before submitting the exam.

3. Submit a exam
* We will get learner id and exam id as input.
* We need to update the entry in the `learner_exams` table (LearnerExam entity). Set the status as SUBMITTED and capture the end time.
* We should only allow the learner to submit the exam if they have started the exam, else we should throw an error.
* A learner can leave few questions unanswered and submit the exam.
* We need to calculate the score of the learner and store it in the `learner_exams` table.
* We can assume that each question will have its own score. If a learner answers a question correctly, then they will get the full score of that question. If a learner answers a question incorrectly or leaves the question, then they will get 0 score for that question.
* We can assume for now that there is no cap on the duration of the exam.
* Return the learner exam object in response.




## Instructions
1. Carefully look at the dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the incomplete methods inside the `MCQExamController`.
4. Implement the `MCQExamService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.