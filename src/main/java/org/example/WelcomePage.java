package org.example;

import org.example.data.RegisterUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class WelcomePage extends JFrame {
    private RegisterUser user;
    JTextField weightGoalField;

    public WelcomePage(RegisterUser user) {
        this.user = user;

        // Set up the frame
        setTitle("Welcome");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome " + user.username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel weightGoalLabel = new JLabel("Enter your weight goal:");
        weightGoalField = new JTextField(15);
        JButton submitButton = new JButton("Submit");
        JButton showGraphButton = new JButton("Show Weight Graph");

        // Add components to the frame
        setLayout(new GridLayout(4, 1));
        add(welcomeLabel);
        add(weightGoalLabel);
        add(weightGoalField);
        add(submitButton);
        add(showGraphButton);

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitWeightGoal();
                NewPage newPage = new NewPage(user);
                newPage.setVisible(true);
                newPage.setSize(300, 200);
            }
        });

        // Add action listener to the show graph button
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeightGraph weightGraph = new WeightGraph(user);
                weightGraph.setVisible(true);
                weightGraph.setSize(800, 600); // Set size for the weight graph window
                dispose(); // Close the welcome page
            }
        });
    }

    void submitWeightGoal() {
        String weightGoal = weightGoalField.getText();
        LocalDate currentDate = LocalDate.now();

        if (weightGoal.isEmpty()) {
            showMessageDialog(this, "Please enter your weight goal.");
            return;
        }

        if (storeWeightGoal(user.username, weightGoal, currentDate)) {
            showMessageDialog(this, "Weight goal submitted successfully!");
        } else {
            showMessageDialog(this, "Failed to submit weight goal. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    boolean storeWeightGoal(String username, String weightGoal, LocalDate date) {
        final String url = "jdbc:mysql://localhost/user";  // Replace with your database URL
        final String dbUsername = "root";  // Replace with your database username
        final String dbPassword = "";  // Replace with your database password

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "INSERT INTO weight_goals (username, weight_goal, date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, weightGoal);
            preparedStatement.setString(3, date.toString());

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void showMessageDialog(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }

    protected void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
}
