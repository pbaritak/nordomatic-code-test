package com.example.building_temperature;

import com.example.building_temperature.exception.BuildingAlreadyExistsException;
import com.example.building_temperature.exception.BuildingNotFoundException;
import com.example.building_temperature.model.Building;
import com.example.building_temperature.repository.BuildingRepository;
import com.example.building_temperature.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingService buildingService;

    @Test
    void createBuilding_success() {
        Building building = new Building("Test Building", 22.0);
        when(buildingRepository.findByName(building.getName())).thenReturn(null);
        when(buildingRepository.save(building)).thenReturn(building);

        Building createdBuilding = buildingService.createBuilding(building);

        assertThat(createdBuilding).isNotNull();
        assertThat(createdBuilding.getName()).isEqualTo(building.getName());
        assertThat(createdBuilding.getTargetTemperature()).isEqualTo(building.getTargetTemperature());
        verify(buildingRepository, times(1)).save(building);
    }

    @Test
    void createBuilding_alreadyExists() {
        Building building = new Building("Test Building", 22.0);
        when(buildingRepository.findByName(building.getName())).thenReturn(building);

        assertThrows(BuildingAlreadyExistsException.class, () -> buildingService.createBuilding(building));

        verify(buildingRepository, never()).save(building);
    }

    @Test
    void getBuilding_success() {
        Long id = 1L;
        Building building = new Building("Test Building", 22.0);
        when(buildingRepository.findById(id)).thenReturn(Optional.of(building));

        Building retrievedBuilding = buildingService.getBuilding(id);

        assertThat(retrievedBuilding).isNotNull();
        assertThat(retrievedBuilding.getName()).isEqualTo(building.getName());
        assertThat(retrievedBuilding.getTargetTemperature()).isEqualTo(building.getTargetTemperature());
    }

    @Test
    void getBuilding_notFound() {
        Long id = 1L;
        when(buildingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.getBuilding(id));
    }

    @Test
    void getAllBuildings() {
        Building building1 = new Building("Test Building 1", 22.0);
        Building building2 = new Building("Test Building 2", 24.0);
        List<Building> buildings = List.of(building1, building2);

        when(buildingRepository.findAll()).thenReturn(buildings);

        List<Building> retrievedBuildings = buildingService.getAllBuildings();

        assertThat(retrievedBuildings).hasSize(2);
        assertThat(retrievedBuildings.get(0).getName()).isEqualTo("Test Building 1");
        assertThat(retrievedBuildings.get(1).getName()).isEqualTo("Test Building 2");
    }

    @Test
    void updateBuilding_success() {
        Long id = 1L;
        Building existingBuilding = new Building("Old Name", 20.0);
        Building updatedBuilding = new Building("New Name", 24.0);
        when(buildingRepository.findById(id)).thenReturn(Optional.of(existingBuilding));
        when(buildingRepository.save(existingBuilding)).thenReturn(existingBuilding);

        Building result = buildingService.updateBuilding(id, updatedBuilding);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updatedBuilding.getName());
        assertThat(result.getTargetTemperature()).isEqualTo(updatedBuilding.getTargetTemperature());
    }

    @Test
    void deleteBuilding_success() {
        Long id = 1L;

        buildingService.deleteBuilding(id);

        verify(buildingRepository, times(1)).deleteById(id);
    }
}