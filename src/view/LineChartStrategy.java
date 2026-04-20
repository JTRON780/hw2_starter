package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import java.util.ArrayList;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XChartPanel;

import model.Transaction;

/**
 * Strategy implementation for building a line chart visualization.
 * Groups transactions by category and renders them as discrete points connected by lines.
 */
public class LineChartStrategy implements ChartStrategy {
    
    /**
     * Builds and returns a JPanel containing a Line Chart (XYChart) of the given transactions.
     * 
     * @param transactions The list of filtered transactions to visualize
     * @param title The title of the chart
     * @return A JPanel containing the generated line chart
     */
    @Override
    public JPanel buildChart(List<Transaction> transactions, String title) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle("Category Index").yAxisTitle("Amount").build();
        
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            categoryTotals.put(t.getCategory(), categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        
        double index = 1.0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            xData.add(index++);
            yData.add(entry.getValue());
        }
        
        // XYChart must have at least one series
        if (xData.isEmpty()) {
            xData.add(0.0);
            yData.add(0.0);
        }
        
        chart.addSeries("Expenses", xData, yData);
        
        return new XChartPanel<>(chart);
    }
}
