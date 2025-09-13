package com.example.splitwise.dtos;

import com.example.splitwise.models.GroupMember;
import lombok.Data;

@Data
public class AddMemberResponseDto {
    private ResponseStatus responseStatus;
    private GroupMember groupMember;
}
