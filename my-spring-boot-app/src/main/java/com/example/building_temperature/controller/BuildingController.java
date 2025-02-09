package com.example.building_temperature.controller;

import com.example.building_temperature.dto.BuildingCreateDto;
import com.example.building_temperature.dto.BuildingResponseDto;
import com.example.building_temperature.model.Building;
import com.example.building_temperature.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Buildings", description = "Operations related to buildings")
@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Operation(summary = "Create a new building", description = "Adds a new building to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Building created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Building with this name already exists")
    })
    @PostMapping
    public ResponseEntity<BuildingResponseDto> createBuilding(@RequestBody BuildingCreateDto buildingCreateDto) {
        Building building = new Building(buildingCreateDto.getName(), buildingCreateDto.getTargetTemperature());
        Building createdBuilding = buildingService.createBuilding(building);

        BuildingResponseDto responseDto = convertToDto(createdBuilding);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBuilding.getId())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @Operation(summary = "Get a building by ID", description = "Retrieves a building by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building found"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponseDto> getBuilding(@PathVariable Long id) {
        Building building = buildingService.getBuilding(id);
        BuildingResponseDto responseDto = convertToDto(building);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Update a building", description = "Updates an existing building's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BuildingResponseDto> updateBuilding(@PathVariable Long id, @RequestBody BuildingCreateDto buildingCreateDto) {
        Building building = new Building(buildingCreateDto.getName(), buildingCreateDto.getTargetTemperature());
        Building updatedBuilding = buildingService.updateBuilding(id, building);
        BuildingResponseDto responseDto = convertToDto(updatedBuilding);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Delete a building", description = "Deletes a building from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Building deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all buildings", description = "Retrieves a list of all buildings in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buildings retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BuildingResponseDto>> getAllBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        List<BuildingResponseDto> responseDtos = buildings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    private BuildingResponseDto convertToDto(Building building) {
        BuildingResponseDto dto = new BuildingResponseDto();
        dto.setId(building.getId());
        dto.setName(building.getName());
        dto.setTargetTemperature(building.getTargetTemperature());
        return dto;
    }
}