package com.example.splitwise.dtos;

import com.example.splitwise.models.User;
import lombok.Data;

import java.util.List;

@Data
public class FetchMembersResponseDto {

    private List<User> members;
    private ResponseStatus responseStatus;
}
