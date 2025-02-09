package com.example.building_temperature.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuildingCreateDto {
    private String name;
    private double targetTemperature;
}
