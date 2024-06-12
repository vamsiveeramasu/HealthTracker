package org.example;

import org.example.data.WeightEntry;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeightDataService {
    static final String url = "jdbc:mysql://localhost/user";
    static final String dbUsername = "root";
    static final String dbPassword = "";

    public static List<WeightEntry> getWeightEntries(String username) {
        List<WeightEntry> weightEntries = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT weight, date, username FROM weight_entries WHERE username = ? ORDER BY date";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double weight = resultSet.getDouble("weight");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String retrievedUsername = resultSet.getString("username");
                weightEntries.add(new WeightEntry(weight, date, retrievedUsername));
//                System.out.println("Weight Entry: " + weightEntries.get(weightEntries.size() - 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weightEntries;
    }
}
