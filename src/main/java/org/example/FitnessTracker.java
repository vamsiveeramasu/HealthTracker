package org.example;

import org.example.data.DailyExerciseTracker;
import org.example.data.Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class FitnessTracker extends JFrame {
    private DailyExerciseTracker dailyTracker;
    private JTextArea displayArea;

    // Existing code

    public DailyExerciseTracker getDailyTracker() {
        return dailyTracker;
    }

    public void setExerciseNameField(String name) {
        exerciseNameField.setText(name);
    }

    public void setCaloriesBurnedField(String calories) {
        caloriesBurnedField.setText(calories);
    }

    public String getDisplayLog() {
        return displayArea.getText();
    }
    private JTextField exerciseNameField;
    private JTextField caloriesBurnedField;

    public FitnessTracker() {
        dailyTracker = new DailyExerciseTracker(LocalDate.now());

        setTitle("Daily Fitness Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Exercise Name:"));
        exerciseNameField = new JTextField();
        inputPanel.add(exerciseNameField);

        inputPanel.add(new JLabel("Calories Burned:"));
        caloriesBurnedField = new JTextField();
        inputPanel.add(caloriesBurnedField);

        JButton addButton = new JButton("Add Exercise");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExercise();
            }
        });
        inputPanel.add(addButton);

        JButton showTotalButton = new JButton("Show Total Calories");
        showTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalCalories();
            }
        });
        inputPanel.add(showTotalButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addExercise() {
        String name = exerciseNameField.getText();
        int calories;
        try {
            calories = Integer.parseInt(caloriesBurnedField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for calories burned.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Exercise exercise = new Exercise(0, name, calories);
        dailyTracker.addExercise(exercise);
        saveExerciseToDatabase(exercise);
        displayArea.append("Added exercise: " + name + " with " + calories + " calories burned.\n");
        exerciseNameField.setText("");
        caloriesBurnedField.setText("");
    }

    void showTotalCalories() {
        int totalCalories = dailyTracker.getTotalCaloriesBurned();
        JOptionPane.showMessageDialog(this, "Total calories burned today: " + totalCalories, "Total Calories", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveExerciseToDatabase(Exercise exercise) {
        String sql = "INSERT INTO exercises (exercise_name, calories_burned, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, exercise.getExerciseName());
            preparedStatement.setInt(2, exercise.getCaloriesBurned());
            preparedStatement.setString(3, LocalDate.now().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FitnessTracker().setVisible(true);
            }
        });
    }
}
