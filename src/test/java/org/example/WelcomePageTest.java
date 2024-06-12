package org.example;

import org.example.data.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class WelcomePageTest {
    private WelcomePage welcomePage;
    private RegisterUser testUser;

    @BeforeEach
    public void setUp() {
        testUser = new RegisterUser("testuser", "test@example.com", "password123");
        welcomePage = Mockito.spy(new WelcomePage(testUser));
    }

    @Test
    public void testSubmitWeightGoal_Success() {
        welcomePage.weightGoalField.setText("70"); // Set weight goal

        // Mock JOptionPane to avoid displaying dialog during the test
        doNothing().when(welcomePage).showMessageDialog(any(Component.class), anyString());

        // Mock database connection to return true
        doReturn(true).when(welcomePage).storeWeightGoal(anyString(), anyString(), any(LocalDate.class));

        // Simulate button click
        welcomePage.submitWeightGoal();

        // Verify if the success message dialog was shown
        verify(welcomePage).showMessageDialog(any(Component.class), eq("Weight goal submitted successfully!"));
    }

    @Test
    public void testSubmitWeightGoal_Failure() {
        welcomePage.weightGoalField.setText("70"); // Set weight goal

        // Mock JOptionPane to avoid displaying dialog during the test
        doNothing().when(welcomePage).showMessageDialog(any(Component.class), anyString(), anyString(), anyInt());

        // Mock database connection to return false
        doReturn(false).when(welcomePage).storeWeightGoal(anyString(), anyString(), any(LocalDate.class));

        // Simulate button click
        welcomePage.submitWeightGoal();

        // Verify if the error dialog was shown
        verify(welcomePage).showMessageDialog(any(Component.class), eq("Failed to submit weight goal. Please try again."), eq("Error"), eq(JOptionPane.ERROR_MESSAGE));
    }

    @Test
    public void testSubmitWeightGoal_EmptyField() {
        welcomePage.weightGoalField.setText(""); // Set weight goal as empty

        // Mock JOptionPane to avoid displaying dialog during the test
        doNothing().when(welcomePage).showMessageDialog(any(Component.class), anyString());

        // Simulate button click
        welcomePage.submitWeightGoal();

        // Verify if the error dialog was shown
        verify(welcomePage).showMessageDialog(any(Component.class), eq("Please enter your weight goal."));
    }
}
