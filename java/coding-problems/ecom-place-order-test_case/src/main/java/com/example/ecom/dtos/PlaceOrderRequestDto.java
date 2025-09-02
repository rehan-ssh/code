package com.example.ecom.dtos;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
public class PlaceOrderRequestDto {
    private int userId;
    private int addressId;
    private List<Pair<Integer, Integer>> orderDetails;
}
