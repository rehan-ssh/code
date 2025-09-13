package com.example.qcommerce.strategies;

import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.utils.DistanceUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("allotNearestPartnerStrategy")
public class AllotNearestPartnerStrategy implements MatchPartnerTaskStrategy{
    @Override
    public List<PartnerTaskMapping> matchPartnersAndTasks(List<Partner> partners, List<Task> tasks) {
        Set<Partner> partnerSet = new HashSet<>(partners);
        List<PartnerTaskMapping> partnerTaskMappings = new ArrayList<>();
        for(Task task: tasks){
            Partner nearestPartner = null;
            double minDistance = Double.MAX_VALUE;
            if(partnerSet.isEmpty()){
                break;
            }
            for(Partner partner: partnerSet){
                double distance = DistanceUtils.calculateDistance(partner.getCurrentLocation(), task.getPickupLocation());
                if(distance < minDistance){
                    minDistance = distance;
                    nearestPartner = partner;
                }
            }
            partnerSet.remove(nearestPartner);
            PartnerTaskMapping partnerTaskMapping = new PartnerTaskMapping();
            partnerTaskMapping.setPartner(nearestPartner);
            partnerTaskMapping.setTask(task);
            partnerTaskMappings.add(partnerTaskMapping);
        }
        return partnerTaskMappings;
    }
}
