package org.example;

import org.example.data.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewPageTest {
    private NewPage newPage;
    private RegisterUser testUser;

    @BeforeEach
    public void setUp() {
        testUser = new RegisterUser(); // Initialize with default values or test-specific values
        newPage = new NewPage(testUser);
    }

    @Test
    public void testMealTrackerButton() {
        JButton mealTrackerBtn = (JButton) findComponentByName(newPage, "Meal Tracker");
        assertNotNull(mealTrackerBtn, "Meal Tracker button not found");

        mealTrackerBtn.doClick(); // Simulate button click

        // Check if the MealEntryForm is displayed
        boolean isMealEntryFormVisible = isFormVisible(MealEntryForm.class);
        assertTrue(isMealEntryFormVisible, "Meal Entry Form should be visible after clicking Meal Tracker button");
    }

    @Test
    public void testCalorieTrackerButton() {
        JButton calorieTrackerBtn = (JButton) findComponentByName(newPage, "Calorie Tracker");
        assertNotNull(calorieTrackerBtn, "Calorie Tracker button not found");

        calorieTrackerBtn.doClick(); // Simulate button click

        // Check if the FitnessTracker form is displayed
        boolean isFitnessTrackerVisible = isFormVisible(FitnessTracker.class);
        assertTrue(isFitnessTrackerVisible, "Fitness Tracker form should be visible after clicking Calorie Tracker button");
    }

    @Test
    public void testWeightTrackerButton() {
        JButton weightTrackerBtn = (JButton) findComponentByName(newPage, "Weight Tracker");
        assertNotNull(weightTrackerBtn, "Weight Tracker button not found");

        weightTrackerBtn.doClick(); // Simulate button click

        // Check if the WeightEntryForm is displayed
        boolean isWeightEntryFormVisible = isFormVisible(WeightEntryForm.class);
        assertTrue(isWeightEntryFormVisible, "Weight Entry Form should be visible after clicking Weight Tracker button");
    }

    // Helper method to find a component by its name (text)
    private JComponent findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                if (jComponent instanceof JButton && ((JButton) jComponent).getText().equals(name)) {
                    return jComponent;
                }
                if (jComponent.getComponentCount() > 0) {
                    JComponent found = findComponentByName(jComponent, name);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

    // Helper method to check if a form of the given class is visible
    private boolean isFormVisible(Class<? extends JFrame> formClass) {
        for (Frame frame : JFrame.getFrames()) {
            if (formClass.isInstance(frame) && frame.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
