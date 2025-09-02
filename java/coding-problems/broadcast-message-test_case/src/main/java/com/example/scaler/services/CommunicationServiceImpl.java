package com.example.scaler.services;

import com.example.scaler.adapters.EmailAdapter;
import com.example.scaler.adapters.WhatsappAdapter;
import com.example.scaler.exceptions.InvalidBatchException;
import com.example.scaler.exceptions.InvalidUserException;
import com.example.scaler.exceptions.UnAuthorizedAccessException;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService{

    private BatchRepository batchRepository;
    private BatchLearnerRepository batchLearnerRepository;
    private UserRepository userRepository;
    private CommunicationRepository communicationRepository;
    private CommunicationLearnerRepository communicationLearnerRepository;
    private EmailAdapter emailAdapter;
    private WhatsappAdapter whatsappAdapter;

    @Autowired
    public CommunicationServiceImpl(BatchRepository batchRepository, BatchLearnerRepository batchLearnerRepository, UserRepository userRepository, CommunicationRepository communicationRepository, CommunicationLearnerRepository communicationLearnerRepository, EmailAdapter emailAdapter, WhatsappAdapter whatsappAdapter) {
        this.batchRepository = batchRepository;
        this.batchLearnerRepository = batchLearnerRepository;
        this.userRepository = userRepository;
        this.communicationRepository = communicationRepository;
        this.communicationLearnerRepository = communicationLearnerRepository;
        this.emailAdapter = emailAdapter;
        this.whatsappAdapter = whatsappAdapter;
    }

    public Communication broadcastMessage(long batchId, long userId, String message) throws InvalidBatchException, InvalidUserException, UnAuthorizedAccessException {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("User not found"));
        if(user.getUserType() != UserType.ADMIN){
            throw new UnAuthorizedAccessException("User is not an admin");
        }

        Batch batch = this.batchRepository.findById(batchId).orElseThrow(() -> new InvalidBatchException("Batch not found"));

        Communication communication = new Communication();
        communication.setBatch(batch);
        communication.setMessage(message);
        communication.setSentBy(user);
        communication = communicationRepository.save(communication);

        // Fetch all learners in the batch
        List<BatchLearner> learners = this.batchLearnerRepository.findByBatchIdAndExitDateIsNull(batchId);

        for(BatchLearner batchLearner: learners){
            boolean emailFailed = false;
            boolean whatsappFailed = false;
            try{
                this.emailAdapter.sendEmail(batchLearner.getLearner().getEmail(), message);
            } catch (Exception e){
                System.out.println("Email failed");
                emailFailed = true;
            }

            try{
                this.whatsappAdapter.sendWhatsappMessage(batchLearner.getLearner().getPhoneNumber(), message);
            } catch (Exception e){
                System.out.println("Whatsapp failed");
                whatsappFailed = true;
            }
            CommunicationLearner communicationLearner = new CommunicationLearner();
            communicationLearner.setCommunication(communication);
            communicationLearner.setLearner(batchLearner.getLearner());
            communicationLearner.setEmailDelivered(emailFailed);
            communicationLearner.setWhatsappDelivered(whatsappFailed);
            this.communicationLearnerRepository.save(communicationLearner);
        }

        return communication;
    }
}
