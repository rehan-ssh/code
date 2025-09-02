package com.example.bmsbookticket.dtos;

import com.example.bmsbookticket.models.Feature;
import com.example.bmsbookticket.models.SeatType;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Data
public class CreateShowRequestDTO {
    private int movieId;
    private int userId;
    private int screenId;
    private Date startTime;
    private Date endTime;
    private List<Feature> features;
    private List<Pair<SeatType, Double>> pricingConfig;
}
