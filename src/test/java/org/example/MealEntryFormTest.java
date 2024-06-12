package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class MealEntryFormTest {
    private MealEntryForm mealEntryForm;

    @BeforeEach
    public void setUp() {
        mealEntryForm = new MealEntryForm();
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources if needed
    }

    @Test
    public void testSaveMealToDatabase_Success() {
        // Simulate successful database save
        try {
            // Replace with your actual database connection details
            String url = "jdbc:mysql://localhost/user";
            String dbUsername = "root";
            String dbPassword = "";

            // Connect to the database
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepare test data
            String foodItem = "Apple";
            double calories = 95.0;
            String date = "2023-06-10";

            // Call the method to test
            mealEntryForm.saveMealToDatabase(foodItem, calories, date);

            // Verify data is saved
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM meals WHERE total_calories = ? AND meal_date = ?");
            preparedStatement.setDouble(1, calories);
            preparedStatement.setString(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next(), "Data was not saved to the database");

            // Close resources
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testSaveMealToDatabase_Failure() {
        // Simulate database failure
        // Mock a SQLException by providing incorrect URL
        String url = "jdbc:mysql://localhost/invalid_database";
        String dbUsername = "root";
        String dbPassword = "";

        // Prepare test data
        String foodItem = "Apple";
        double calories = 95.0;
        String date = "2023-06-10";

        // Call the method to test
        mealEntryForm.saveMealToDatabase(foodItem, calories, date);

        // Since the database connection should fail, no data should be saved
        fail("No SQLException thrown");
    }
}
