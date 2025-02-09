package com.example.building_temperature.database;

import com.example.building_temperature.repository.BuildingRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Slf4j
public class DatabaseInitializer {

    private final DataSource dataSource;
    private final BuildingRepository buildingRepository; // Inject BuildingRepository

    public DatabaseInitializer(DataSource dataSource, BuildingRepository buildingRepository) {
        this.dataSource = dataSource;
        this.buildingRepository = buildingRepository;
    }

    @PostConstruct
    public void initialize() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String createBuildingTable = """
                    CREATE TABLE IF NOT EXISTS building (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(255) UNIQUE NOT NULL,
                        target_temperature DOUBLE NOT NULL
                    );
                    """;
            statement.execute(createBuildingTable);

            if (buildingRepository.count() == 0) {
                initializeBuildings(connection);
            }

        } catch (SQLException e) {
            log.error("Failed to initialize database schema", e);
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    private void initializeBuildings(Connection connection) throws SQLException {
        String insertBuildingSQL = "INSERT INTO building (name, target_temperature) VALUES (?, ?)";

        try (PreparedStatement insertBuildingStmt = connection.prepareStatement(insertBuildingSQL)) {
            insertBuildingStmt.setString(1, "Building A");
            insertBuildingStmt.setDouble(2, 22.0);
            insertBuildingStmt.addBatch();

            insertBuildingStmt.setString(1, "Building B");
            insertBuildingStmt.setDouble(2, 24.5);
            insertBuildingStmt.addBatch();

            insertBuildingStmt.setString(1, "Building C");
            insertBuildingStmt.setDouble(2, 20.0);
            insertBuildingStmt.addBatch();

            insertBuildingStmt.executeBatch();
        }
    }
}