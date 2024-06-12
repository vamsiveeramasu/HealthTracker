package org.example.data;

import java.util.ArrayList;
import java.util.List;

public class WeightTracker {
    private List<WeightEntry> weightEntries;

    public WeightTracker() {
        this.weightEntries = new ArrayList<>();
    }

    public void addWeightEntry(WeightEntry weightEntry) {
        weightEntries.add(weightEntry);
    }

    public List<WeightEntry> getWeightEntries() {
        return weightEntries;
    }

    // Other methods for displaying the weight entries in a line graph
    // ...
}
