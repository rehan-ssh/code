# Implement functionality which will broadcast important messages to learners

## Problem Statement

You are building web application for Scaler Academy. As a part of this system, you need to implement a functionality which will broadcast important messages to learners. This functionality will be used by the admin team to broadcast important messages to learners.

## Requirements

* This functionality will be available for admin users only.
* The input to this functionality will be a message, a batch id and user id (of the user trying to broadcast the message).
* This message should be sent to all the learners via email and a whatsapp direct message.
* Details about every message that is being sent is captured in a table `communications` tracked via Communication entity.
* Whatsapp message and email will be sent via Sendgrid which is a third party service. This library can change in the future, so please keep this integration extensible.
* Details about every message being sent should be stored in a table named `communication_learners` tracked via CommunicationLearner entity.
* Note: Message should only be sent to learners whose current batch id matches the batch id provided in the input.
* If a learner was part of this batch in the past, but is not part of this batch anymore, then the message should not be sent to this learner.
* Return communication object in response once the emails and whatsapp messages are sent.


## Instructions
1. Carefully look at the dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `broadcastMessage` method inside the `CommunicationController`.
4. Implement the `CommunicationService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.