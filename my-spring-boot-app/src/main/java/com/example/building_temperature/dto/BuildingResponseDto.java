package com.example.building_temperature.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuildingResponseDto {
    private Long id;
    private String name;
    private double targetTemperature;

}
