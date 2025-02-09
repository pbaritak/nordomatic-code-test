package com.example.building_temperature.exception;

public class BuildingAlreadyExistsException extends IllegalStateException {
    public BuildingAlreadyExistsException(String message) {
        super(message);
    }
}