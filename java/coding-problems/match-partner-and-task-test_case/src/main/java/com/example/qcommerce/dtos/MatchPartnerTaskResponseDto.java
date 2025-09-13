package com.example.qcommerce.dtos;

import com.example.qcommerce.models.PartnerTaskMapping;
import lombok.Data;

import java.util.List;

@Data
public class MatchPartnerTaskResponseDto {
    private List<PartnerTaskMapping> partnerTaskMappings;
    private ResponseStatus responseStatus;
}
