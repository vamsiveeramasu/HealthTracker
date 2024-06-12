package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MealEntryForm extends JFrame {
    private JTextField foodItemField;
    private JTextField caloriesField;
    private JFormattedTextField dateField;
    private JButton submitButton;

    public MealEntryForm() {
        setTitle("Meal Entry Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel foodItemLabel = new JLabel("Food Item:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(foodItemLabel, constraints);

        foodItemField = new JTextField(20);
        foodItemField.setName("foodItemField"); // Set name for test identification
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(foodItemField, constraints);

        JLabel caloriesLabel = new JLabel("Calories:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(caloriesLabel, constraints);

        caloriesField = new JTextField(20);
        caloriesField.setName("caloriesField"); // Set name for test identification
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(caloriesField, constraints);

        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(dateLabel, constraints);

        dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        dateField.setName("dateField"); // Set name for test identification
        dateField.setColumns(20);
        dateField.setValue(new Date());
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(dateField, constraints);

        submitButton = new JButton("Submit");
        submitButton.setName("submitButton"); // Set name for test identification
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitMealData();
            }
        });

        add(panel);
    }

    public void submitMealData() {
        String foodItem = foodItemField.getText();
        double calories;
        try {
            calories = Double.parseDouble(caloriesField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for calories.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String date = dateField.getText();

        if (foodItem.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveMealToDatabase(foodItem, calories, date);
        clearFields();
    }

    void saveMealToDatabase(String foodItem, double calories, String date) {
        final String url = "jdbc:mysql://localhost/user";
        final String dbUsername = "root";
        final String dbPassword = "";  // Replace with your database password

        String sql = "INSERT INTO meals (food_id, calories, date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, getNextMealId(conn));
            preparedStatement.setDouble(2, calories);
            preparedStatement.setString(3, date);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Meal data saved successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getNextMealId(Connection conn) {
        String query = "SELECT MAX(food_id) FROM meals";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 1; // Default to 1 if table is empty
    }

    private void clearFields() {
        foodItemField.setText("");
        caloriesField.setText("");
        dateField.setValue(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MealEntryForm form = new MealEntryForm();
                form.setVisible(true);
            }
        });
    }
}
