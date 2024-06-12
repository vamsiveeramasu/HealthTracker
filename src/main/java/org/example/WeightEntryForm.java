package org.example;

import org.example.data.RegisterUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

public class WeightEntryForm extends JFrame {
    static final String url = "jdbc:mysql://localhost/user";
    static final String dbUsername = "root";
    static final String dbPassword = "";

    private RegisterUser user;
    private JLabel usernameLabel;
    private JTextField weightField;

    public WeightEntryForm(RegisterUser user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Weight Entry Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel weightLabel = new JLabel("Enter your weight: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(weightLabel, constraints);

        weightField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(weightField, constraints);

        if (user != null) {
            usernameLabel = new JLabel("Username: " + user.username);
        } else {
            usernameLabel = new JLabel("Username: Not logged in");
        }
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(usernameLabel, constraints);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitWeightData();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 5, 5, 5);
        panel.add(submitButton, constraints);

        add(panel, BorderLayout.CENTER);
    }

    private void submitWeightData() {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "You must be logged in to add a weight entry.");
            return;
        }

        double weight = Double.parseDouble(weightField.getText());
        LocalDate today = LocalDate.now();

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO weight_entries (weight, date,username) VALUES (?, ?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, weight);
            statement.setString(3, user.username);
            statement.setString(2, today.toString());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Weight entry added successfully.");
            weightField.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding weight entry.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Assuming you have a way to obtain the logged-in user or null if not logged in
                RegisterUser loggedInUser = getLoggedInUser();
                WeightEntryForm form = new WeightEntryForm(loggedInUser);
                form.setVisible(true);
            }
        });
    }

    // You need to provide the implementation for this method
    private static RegisterUser getLoggedInUser() {
        // Return the currently logged-in user or null if not logged in
        return null; // Replace with your actual implementation
    }
}