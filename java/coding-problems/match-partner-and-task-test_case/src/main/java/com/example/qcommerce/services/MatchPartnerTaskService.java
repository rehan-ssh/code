package com.example.qcommerce.services;

import com.example.qcommerce.models.PartnerTaskMapping;

import java.util.List;

public interface MatchPartnerTaskService {

    public List<PartnerTaskMapping> matchPartnersAndTasks(List<Long> partnerIds, List<Long> taskIds);
}
