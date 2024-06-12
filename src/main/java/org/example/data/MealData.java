package org.example.data;

public class MealData {
    private int id;
    private String foodItem;
    private double calories;
    private String username;
    private String date;

    public MealData(int id, String foodItem, double calories, String username, String date) {
        this.id = id;
        this.foodItem = foodItem;
        this.calories = calories;
        this.username = username;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
