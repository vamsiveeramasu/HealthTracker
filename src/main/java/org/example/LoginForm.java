package org.example;

import org.example.data.RegisterUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame implements ActionListener {
    JButton b1, b2;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    final JTextField textField1, textField2;

    public LoginForm() {
        userLabel = new JLabel("Enter email address");
        textField1 = new JTextField(15);

        passLabel = new JLabel("Enter password");
        textField2 = new JPasswordField(15);

        b1 = new JButton("LOGIN");
        b2 = new JButton("REGISTER");

        newPanel = new JPanel(new GridLayout(4, 1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(b1);
        newPanel.add(b2);

        add(newPanel, BorderLayout.CENTER);

        b1.addActionListener(this);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterPage registerPage = new RegisterPage();
                registerPage.setVisible(true);
                registerPage.setSize(300, 200);
            }
        });

        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();

        user = getAuthenticatedUser(userValue, passValue);

        if (user != null) {
            WelcomePage page = new WelcomePage(user);
            page.setVisible(true);
            JLabel wel_label = new JLabel("Welcome: " + user.username);
            page.getContentPane().add(wel_label);

            WeightEntryForm weightEntryForm = new WeightEntryForm(user);
            weightEntryForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password", "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    public RegisterUser user;

    RegisterUser getAuthenticatedUser(String userValue, String passValue) {
        RegisterUser user = null;
        final String url = "jdbc:mysql://localhost/user";
        final String username = "root";
        final String password = "";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userValue);
            preparedStatement.setString(2, passValue);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new RegisterUser();
                user.username = resultSet.getString("username");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
            }

            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.setSize(300, 200);
        loginForm.setVisible(true);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
