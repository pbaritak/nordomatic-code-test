package com.example.building_temperature.service;

import com.example.building_temperature.exception.BuildingAlreadyExistsException;
import com.example.building_temperature.exception.BuildingNotFoundException;
import com.example.building_temperature.model.Building;
import com.example.building_temperature.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService implements IBuildingService {

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public Building createBuilding(Building building) {
        if (buildingRepository.findByName(building.getName()) != null) {
            throw new BuildingAlreadyExistsException("Building with this name already exists");
        }
        return buildingRepository.save(building);
    }

    @Override
    public Building getBuilding(Long id) {
        return buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
    }

    @Override
    public Building updateBuilding(Long id, Building building) {
        Building existingBuilding = getBuilding(id);
        existingBuilding.setName(building.getName());
        existingBuilding.setTargetTemperature(building.getTargetTemperature());
        return buildingRepository.save(existingBuilding);
    }

    @Override
    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }
}