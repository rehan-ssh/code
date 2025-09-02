package com.example.scaler.controllers;

import com.example.scaler.dtos.BroadcastMessageRequestDto;
import com.example.scaler.dtos.BroadcastMessageResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestCommunicationController {
    @Autowired
    private BatchLearnerRepository batchLearnerRepository;
    @Autowired
    private CommunicationLearnerRepository communicationLearnerRepository;
    @Autowired
    private CommunicationController communicationController;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private CommunicationRepository communicationRepository;
    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        batchLearnerRepository.deleteAll();
        communicationLearnerRepository.deleteAll();
        communicationRepository.deleteAll();
        batchRepository.deleteAll();
        learnerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testBroadcastMessage_UnAuthorizedException(){
        User user = new User();
        user.setName("User 1");
        user.setUserType(UserType.INSTRUCTOR);
        user = userRepository.save(user);

        BroadcastMessageRequestDto requestDto = new BroadcastMessageRequestDto();
        requestDto.setBatchId(1);
        requestDto.setMessage("Hello");
        requestDto.setUserId(user.getId());

        BroadcastMessageResponseDto responseDto = communicationController.broadcastMessage(requestDto);

        assertNotNull(responseDto, "Response cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus(), "Response status should be failure");
    }

    @Test
    public void testBroadcastMessage_UserNotFound(){
        User user = new User();
        user.setName("User 1");
        user.setUserType(UserType.INSTRUCTOR);
        user = userRepository.save(user);

        BroadcastMessageRequestDto requestDto = new BroadcastMessageRequestDto();
        requestDto.setBatchId(1);
        requestDto.setMessage("Hello");
        requestDto.setUserId(user.getId() * 100);

        BroadcastMessageResponseDto responseDto = communicationController.broadcastMessage(requestDto);
        assertNotNull(responseDto, "Response cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus(), "Response status should be failure");
    }

    @Test
    public void testBroadcastMessage_BatchNotFound(){
        User user = new User();
        user.setName("User 1");
        user.setUserType(UserType.ADMIN);
        user = userRepository.save(user);

        BroadcastMessageRequestDto requestDto = new BroadcastMessageRequestDto();
        requestDto.setBatchId(100);
        requestDto.setMessage("Hello");
        requestDto.setUserId(user.getId());

        BroadcastMessageResponseDto responseDto = communicationController.broadcastMessage(requestDto);
        assertNotNull(responseDto, "Response cannot be null");
        assertEquals(ResponseStatus.FAILURE, responseDto.getStatus(), "Response status should be failure");
    }

    @Test
    public void testBroadcastMessage_BatchWith2ActiveLearners(){
        User user = new User();
        user.setName("User 1");
        user.setUserType(UserType.ADMIN);
        user = userRepository.save(user);


        Learner learner1 = new Learner();
        learner1.setName("Learner 1");
        learner1.setPhoneNumber("1234567890");
        learner1 = learnerRepository.save(learner1);

        Learner learner2 = new Learner();
        learner2.setName("Learner 2");
        learner2.setPhoneNumber("1234567891");
        learner2 = learnerRepository.save(learner2);

        Learner learner3 = new Learner();
        learner3.setName("Learner 3");
        learner3.setPhoneNumber("1234567892");
        learner3 = learnerRepository.save(learner3);

        Batch batch = new Batch();
        batch.setName("Batch 1");
        batch = batchRepository.save(batch);

        BatchLearner batchLearner1 = new BatchLearner();
        batchLearner1.setBatch(batch);
        batchLearner1.setLearner(learner1);
        batchLearner1.setEntryDate(new Date(2021, 1, 1));
        batchLearner1 = batchLearnerRepository.save(batchLearner1);

        BatchLearner batchLearner2 = new BatchLearner();
        batchLearner2.setBatch(batch);
        batchLearner2.setLearner(learner2);
        batchLearner2.setEntryDate(new Date(2021, 1, 1));
        batchLearner2 = batchLearnerRepository.save(batchLearner2);

        BatchLearner batchLearner3 = new BatchLearner();
        batchLearner3.setBatch(batch);
        batchLearner3.setLearner(learner3);
        batchLearner3.setEntryDate(new Date(2021, 1, 1));
        batchLearner3.setExitDate(new Date(2021, 1, 2));
        batchLearner3 = batchLearnerRepository.save(batchLearner3);

        BroadcastMessageRequestDto requestDto = new BroadcastMessageRequestDto();
        requestDto.setBatchId(1);
        requestDto.setMessage("Hello");
        requestDto.setUserId(user.getId());

        BroadcastMessageResponseDto responseDto = communicationController.broadcastMessage(requestDto);
        assertNotNull(responseDto, "Response cannot be null");
        assertEquals(ResponseStatus.SUCCESS, responseDto.getStatus(), "Response status should be success");
        assertNotNull(responseDto.getCommunication(), "Communication cannot be null");

        List<CommunicationLearner> list = this.communicationLearnerRepository.findAll().stream().filter(cl -> cl.getCommunication().getId() == responseDto.getCommunication().getId()).toList();
        assertEquals(2, list.size(), "Message should be sent to 2 learners");
    }
}
