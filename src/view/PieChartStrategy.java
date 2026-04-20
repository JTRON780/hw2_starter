package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import model.Transaction;

/**
 * Strategy implementation for building a pie chart visualization.
 * Groups transactions by category and calculates aggregate amounts.
 */
public class PieChartStrategy implements ChartStrategy {
    @Override
    public JPanel buildChart(List<Transaction> transactions, String title) {
        PieChart chart = new PieChartBuilder().width(800).height(600).title(title).build();
        
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            categoryTotals.put(t.getCategory(), categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }
        
        return new XChartPanel<>(chart);
    }
}
