package org.example;

import org.example.FitnessTracker;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class FitnessTrackerTest {
    private FitnessTracker fitnessTracker;

    @BeforeEach
    public void setUp() {
        fitnessTracker = new FitnessTracker();
    }

    @Test
    public void testAddExercise_ValidData() {
        // Simulate valid user input
        fitnessTracker.setExerciseNameField("Running");
        fitnessTracker.setCaloriesBurnedField("200");

        // Call the method to test
        fitnessTracker.addExercise();

        // Check if exercise is added
        assertEquals(1, fitnessTracker.getDailyTracker().getExercises().size());

        // Check if display area is updated
        assertTrue(fitnessTracker.getDisplayLog().contains("Added exercise: Running with 200 calories burned."));
    }

    @Test
    public void testAddExercise_InvalidCalories() {
        // Simulate invalid calories input
        fitnessTracker.setExerciseNameField("Running");
        fitnessTracker.setCaloriesBurnedField("-200");

        // Call the method to test
        fitnessTracker.addExercise();

        // Check if exercise is not added
        assertEquals(0, fitnessTracker.getDailyTracker().getExercises().size());

        // Check if display area is not updated
        assertFalse(fitnessTracker.getDisplayLog().contains("Running"));
    }

    @Test
    public void testShowTotalCalories() {
        // Simulate valid user input
        fitnessTracker.setExerciseNameField("Running");
        fitnessTracker.setCaloriesBurnedField("200");
        fitnessTracker.addExercise();

        // Simulate valid user input
        fitnessTracker.setExerciseNameField("Swimming");
        fitnessTracker.setCaloriesBurnedField("300");
        fitnessTracker.addExercise();

        // Call the method to test
        fitnessTracker.showTotalCalories();

        // Check if dialog is displayed with correct total calories
        JOptionPane pane = getOptionPaneWithMessage();
        assertNotNull(pane);
        assertEquals("Total calories burned today: 500", pane.getMessage());
    }

    private JOptionPane getOptionPaneWithMessage() {
        JOptionPane pane = null;
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                Container container = (Container) ((JDialog) window).getContentPane().getComponent(0);
                if (container instanceof JOptionPane) {
                    pane = (JOptionPane) container;
                    break;
                }
            }
        }
        return pane;
    }
}
