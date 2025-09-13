package com.example.splitwise.dtos;

import lombok.Data;

@Data
public class DeleteGroupRequestDto {
    private long groupId;
    private long userId;
}
