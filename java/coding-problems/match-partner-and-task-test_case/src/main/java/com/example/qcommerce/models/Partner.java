package com.example.qcommerce.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Partner extends BaseModel{
    private String name;
    @Embedded
    private Location currentLocation;
}
