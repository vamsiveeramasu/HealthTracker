package org.example;

import org.example.data.RegisterUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPage extends JFrame {
    private RegisterUser user;

    public NewPage(RegisterUser user) {
        this.user = user;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Welcome");
        setSize(800, 700);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Panel to hold buttons
        JButton mealTrackerBtn = new JButton("Meal Tracker");
        JButton calorieTrackerBtn = new JButton("Calorie Tracker");
        JButton weightTrackerBtn = new JButton("Weight Tracker");

        // Add action listeners to buttons
        mealTrackerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MealEntryForm mealEntryForm = new MealEntryForm();
                mealEntryForm.setVisible(true);
                mealEntryForm.setSize(300, 200); // Set size for the login page window
            }
        });

        calorieTrackerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FitnessTracker fitnessTracker = new FitnessTracker();
                fitnessTracker.setVisible(true);
                fitnessTracker.setSize(300, 200);
            }
        });

        weightTrackerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WeightEntryForm weightEntryForm = new WeightEntryForm(user);
                weightEntryForm.setVisible(true);
                weightEntryForm.setSize(300, 200);
            }
        });

        // Add buttons to the panel
        buttonPanel.add(mealTrackerBtn);
        buttonPanel.add(calorieTrackerBtn);
        buttonPanel.add(weightTrackerBtn);

        // Add panel to the frame
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RegisterUser user = new RegisterUser(); // Assuming a default constructor
                NewPage page = new NewPage(user);
                page.setVisible(true);
            }
        });
    }
}
