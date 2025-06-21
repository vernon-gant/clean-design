package com.example.task01;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JDBCStorageTests {
    private Connection connection;
    private JDBCStorage storage;

    @BeforeAll
    void initDb() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE storage (id INT AUTO_INCREMENT PRIMARY KEY, data VARCHAR(255))");
        }
        storage = new JDBCStorage(connection);
    }

    @BeforeEach
    void cleanDb() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM storage");
            stmt.execute("TRUNCATE TABLE storage RESTART IDENTITY");
        }
    }

    @AfterAll
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void GivenEmptyDatabase_WhenInsertingNewEntry_ThenItMustBeAvailableUnderIdZero() {
        // Given

        // When
        String entry = "hello";
        storage.save(entry);

        // Then
        then(storage.retrieve(0)).isEqualTo(entry);
    }

    @Test
    public void GivenEmptyDatabase_WhenInsertingMultipleEntries_ThenTheyMustBeRetrievableUnderCorrespondingIndexes() {
        // Given

        // When
        List<String> entries = Arrays.asList("foo", "bar", "baz");
        entries.forEach(storage::save);

        // Then
        for (int i = 0; i < entries.size(); i++) {
            then(storage.retrieve(i)).isEqualTo(entries.get(i));
        }
    }
}
