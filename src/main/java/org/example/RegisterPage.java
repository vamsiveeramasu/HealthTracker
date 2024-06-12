package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterPage extends JFrame implements ActionListener {
    JLabel userLabel, passLabel, emailLabel;
    JTextField textField1, textField2;
    JPasswordField textField3;
    JButton registerButton, loginButton;

    RegisterPage() {
        // Create labels
        userLabel = new JLabel("Enter username");
        emailLabel = new JLabel("Enter email address");
        passLabel = new JLabel("Enter password");

        // Create text fields
        textField1 = new JTextField(15);
        textField2 = new JTextField(15);
        textField3 = new JPasswordField(15);

        // Create buttons
        registerButton = new JButton("REGISTER");
        loginButton = new JButton("LOGIN");

        // Create panel
        JPanel newPanel = new JPanel(new GridLayout(5, 1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(emailLabel);
        newPanel.add(textField2);
        newPanel.add(passLabel);
        newPanel.add(textField3);
        newPanel.add(registerButton);
        newPanel.add(loginButton);

        add(newPanel, BorderLayout.CENTER);
        setTitle("REGISTER FORM");

        registerButton.addActionListener(this);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to LoginPage (assuming LoginForm class exists)
                LoginForm loginPage = new LoginForm();
                loginPage.setVisible(true);
                loginPage.setSize(300, 200); // Set size for the login page window
                dispose(); // Dispose the current RegisterPage
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        String username = textField1.getText();
        String email = textField2.getText();
        String password = new String(textField3.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (registerUser(username, email, password)) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            // Navigate to LoginPage
            LoginForm loginPage = new LoginForm();
            loginPage.setVisible(true);
            loginPage.setSize(300, 200); // Set size for the login page window
            this.dispose(); // Dispose the current RegisterPage
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean registerUser(String username, String email, String password) {
        final String url = "jdbc:mysql://localhost/user";  // Replace with your database URL
        final String dbUsername = "root";  // Replace with your database username
        final String dbPassword = "";  // Replace with your database password

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setSize(300, 200);  // Set size for the register page window
        registerPage.setVisible(true);
    }
}
