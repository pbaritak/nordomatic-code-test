package com.example.building_temperature.service;

import com.example.building_temperature.model.Building;

import java.util.List;

public interface IBuildingService {
    Building createBuilding(Building building);
    Building getBuilding(Long id);
    Building updateBuilding(Long id, Building building);
    void deleteBuilding(Long id);
    List<Building> getAllBuildings();
}