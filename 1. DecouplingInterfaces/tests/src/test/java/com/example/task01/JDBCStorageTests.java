package com.example.task01;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.assertj.core.api.BDDAssertions.then;

public class JDBCStorageTests {
    private Connection connection;
    private JDBCStorage storage;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE storage (id INT AUTO_INCREMENT PRIMARY KEY, data VARCHAR(255))");
        }
        storage = new JDBCStorage(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void GivenEmptyDatabase_WhenInsertingNewEntry_ThenItMustBeAvailableUnderIdZero() {
        // Given empty database

        // When inserting new entry
        String entry = "hello";
        storage.save(entry);

        // Then it must be available under index 0
        then(storage.retrieve(0)).isEqualTo(entry);
    }
}