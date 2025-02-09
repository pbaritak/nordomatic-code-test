package com.example.building_temperature.exception;

import java.util.NoSuchElementException;

public class BuildingNotFoundException extends NoSuchElementException {
    public BuildingNotFoundException(String message) {
        super(message);
    }
}