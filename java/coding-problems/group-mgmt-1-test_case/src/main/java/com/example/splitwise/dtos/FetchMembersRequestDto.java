package com.example.splitwise.dtos;

import lombok.Data;

@Data
public class FetchMembersRequestDto {
    private long groupId;
    private long memberId;
}
