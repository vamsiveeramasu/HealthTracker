package org.example.data;

import java.time.LocalDate;

public class WeightEntry {
    private double weight;
    private LocalDate date;
    private String username;

    public WeightEntry(double weight, LocalDate date, String username) {
        this.weight = weight;
        this.date = date;
        this.username = username;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "WeightEntry{" +
                "weight=" + weight +
                ", date=" + date +
                ", username='" + username + '\'' +
                '}';
    }
}
