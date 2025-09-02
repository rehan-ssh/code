package com.example.qcommerce.models;

import lombok.Data;

@Data
public class PartnerTaskMapping extends BaseModel{
    private Partner partner;
    private Task task;
}
