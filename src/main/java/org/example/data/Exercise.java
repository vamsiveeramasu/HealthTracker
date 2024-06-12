package org.example.data;

public class Exercise {
    private int id;
    private String exerciseName;
    private int caloriesBurned;

    public Exercise(int id, String exerciseName, int caloriesBurned) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.caloriesBurned = caloriesBurned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}