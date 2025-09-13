package com.example.splitwise.dtos;

import lombok.Data;

@Data
public class CreateGroupRequestDto {
    private String name;
    private String description;
    private long creatorUserId;
}
