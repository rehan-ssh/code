package com.example.splitwise.dtos;

import com.example.splitwise.models.Group;
import lombok.Data;

@Data
public class CreateGroupResponseDto {
    private Group group;
    private ResponseStatus responseStatus;
}
