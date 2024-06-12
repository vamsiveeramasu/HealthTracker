package org.example.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MealPage {
    final String url = "jdbc:mysql://localhost/user";  // Replace with your database URL
    final String dbUsername = "root";  // Replace with your database username
    final String dbPassword = "";  // Replace with your database password

    public void insertMealData(MealData mealData) {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO meals (food_item, calories, username, meal_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mealData.getFoodItem());
            statement.setDouble(2, mealData.getCalories());
            statement.setString(3, mealData.getUsername());
            statement.setString(4, mealData.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
