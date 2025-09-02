package com.example.qcommerce.services;

import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.repositories.PartnerRepository;
import com.example.qcommerce.repositories.TaskRepository;
import com.example.qcommerce.strategies.MatchPartnerTaskStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchPartnerTaskServiceImpl implements MatchPartnerTaskService{

    private PartnerRepository partnerRepository;
    private TaskRepository taskRepository;
    private MatchPartnerTaskStrategy matchPartnerTaskStrategy;

    @Autowired
    public MatchPartnerTaskServiceImpl(PartnerRepository partnerRepository, TaskRepository taskRepository, 
    @Qualifier("allotNearestPartnerStrategy") MatchPartnerTaskStrategy matchPartnerTaskStrategy) {
        this.partnerRepository = partnerRepository;
        this.taskRepository = taskRepository;
        this.matchPartnerTaskStrategy = matchPartnerTaskStrategy;
    }

    @Override
    public List<PartnerTaskMapping> matchPartnersAndTasks(List<Long> partnerIds, List<Long> taskIds) {
        List<Partner> partners = partnerRepository.findAllById(partnerIds);
        List<Task> tasks = taskRepository.findAllById(taskIds);
        return matchPartnerTaskStrategy.matchPartnersAndTasks(partners, tasks);
    }
}
