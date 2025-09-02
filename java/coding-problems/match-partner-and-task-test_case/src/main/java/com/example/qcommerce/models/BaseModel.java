package com.example.qcommerce.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
}
