package org.example;

import org.example.data.RegisterUser;
import org.example.data.WeightEntry;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class WeightGraph extends JFrame {
    private RegisterUser user;

    public WeightGraph(RegisterUser user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Weight Graph");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<WeightEntry> weightEntries = WeightDataService.getWeightEntries(user.getUsername());

        // Debugging: Print all retrieved weight entries
        for (WeightEntry entry : weightEntries) {
            System.out.println("Retrieved Weight Entry: " + entry);
        }

        DefaultCategoryDataset dataset = createDataset(weightEntries);

        // Debugging: Print dataset entries
        System.out.println("Dataset row count: " + dataset.getRowCount());
        System.out.println("Dataset column count: " + dataset.getColumnCount());
        for (int row = 0; row < dataset.getRowCount(); row++) {
            for (int col = 0; col < dataset.getColumnCount(); col++) {
                System.out.println("Dataset value at row " + row + ", column " + col + ": " + dataset.getValue(row, col));
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Daily Weight Entries",
                "Date",
                "Weight",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<WeightEntry> weightEntries) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (WeightEntry entry : weightEntries) {
            dataset.addValue(entry.getWeight(), "Weight", entry.getDate().toString());
        }
        return dataset;
    }
}
