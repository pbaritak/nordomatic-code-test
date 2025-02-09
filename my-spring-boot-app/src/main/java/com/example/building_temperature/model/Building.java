package com.example.building_temperature.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private double targetTemperature;

    public Building() {}

    public Building(String name, double targetTemperature) {
        this.name = name;
        this.targetTemperature = targetTemperature;
    }


}