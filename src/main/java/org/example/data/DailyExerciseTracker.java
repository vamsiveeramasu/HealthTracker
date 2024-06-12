package org.example.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyExerciseTracker {
    private LocalDate date;
    private List<Exercise> exercises;
    private int totalCaloriesBurned;

    public DailyExerciseTracker(LocalDate date) {
        this.date = date;
        this.exercises = new ArrayList<>();
        this.totalCaloriesBurned = 0;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        totalCaloriesBurned += exercise.getCaloriesBurned();
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
