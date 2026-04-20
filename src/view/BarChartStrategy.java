package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import java.util.ArrayList;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import model.Transaction;

public class BarChartStrategy implements ChartStrategy {
    @Override
    public JPanel buildChart(List<Transaction> transactions, String title) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title).xAxisTitle("Category").yAxisTitle("Amount").build();
        
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            categoryTotals.put(t.getCategory(), categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
        }
        
        List<String> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            xData.add(entry.getKey());
            yData.add(entry.getValue());
        }
        
        // CategoryChart must have at least one series
        if (xData.isEmpty()) {
            xData.add("None");
            yData.add(0.0);
        }
        
        chart.addSeries("Expenses", xData, yData);
        
        return new XChartPanel<>(chart);
    }
}
