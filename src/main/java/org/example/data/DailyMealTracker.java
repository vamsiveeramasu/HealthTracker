package org.example.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyMealTracker {
    private List<MealData> dailyMeals;
    private LocalDate date;
    private int totalCalories;

    public DailyMealTracker(LocalDate date) {
        this.date = date;
        this.dailyMeals = new ArrayList<>();
        this.totalCalories = 0;
    }

    public void addMeal(String foodItem, int calories) {
        String username = "user123"; // Replace with the actual username
        String dateString = date.toString();
        MealData mealData = new MealData(0, foodItem, calories, username, dateString);
        dailyMeals.add(mealData);
        totalCalories += calories;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public List<MealData> getDailyMeals() {
        return dailyMeals;
    }
}
