package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.BatchLearner;
import com.example.scaler.models.Learner;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.BatchLearnerRepository;
import com.example.scaler.repositories.LearnerRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LearnerServiceImpl implements LearnerService{

    private ScheduledLectureRepository scheduledLectureRepository;

    private LearnerRepository learnerRepository;

    private BatchLearnerRepository batchLearnerRepository;

    @Autowired
    public LearnerServiceImpl(ScheduledLectureRepository scheduledLectureRepository, LearnerRepository learnerRepository, BatchLearnerRepository batchLearnerRepository) {
        this.scheduledLectureRepository = scheduledLectureRepository;
        this.learnerRepository = learnerRepository;
        this.batchLearnerRepository = batchLearnerRepository;
    }

    @Override
    public List<ScheduledLecture> fetchTimeline(long learnerId) throws InvalidLearnerException {

        Optional<Learner> optionalLearner = learnerRepository.findById(learnerId);
        if(optionalLearner.isEmpty()){
            throw new InvalidLearnerException("Learner not found");
        }

        List<BatchLearner> learnerBatches = batchLearnerRepository.findByLearnerIdOrderByEntryDate(learnerId);

        if(learnerBatches.isEmpty()){
            throw new InvalidLearnerException("Learner not enrolled in any batch");
        }

        List<ScheduledLecture> timeline = new ArrayList<>();
        for (BatchLearner learnerBatch : learnerBatches) {
            List<ScheduledLecture> scheduledLectures;
            if(learnerBatch.getExitDate() != null) {
                scheduledLectures = this.scheduledLectureRepository.findByBatchIdAndLectureStartTimeAfterAndLectureEndTimeBeforeOrderByLectureStartTime(learnerBatch.getBatch().getId(), learnerBatch.getEntryDate(), learnerBatch.getExitDate());
            }else{
                scheduledLectures = this.scheduledLectureRepository.findByBatchIdAndLectureStartTimeAfterOrderByLectureStartTime(learnerBatch.getBatch().getId(), learnerBatch.getEntryDate());
            }
            timeline.addAll(scheduledLectures);
        }

        return timeline;
    }
}
