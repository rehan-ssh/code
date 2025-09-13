package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "theatres")
public class Theatre extends BaseModel{

//    @Getter
//    @Setter
    private String name;

    private String address;

    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens;

    @ManyToOne
    private City city;

}
