package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Transaction;

/**
 * A JPanel view representing the Analysis tab.
 * Contains user input filters for generating charts and a display container for the chart itself.
 */
public class AnalysisPanelView extends JPanel {
    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<String> chartTypeComboBox;
    private JButton generateButton;
    private JPanel chartContainer;

    public AnalysisPanelView() {
        setLayout(new BorderLayout());

        // Top panel for controls
        JPanel controlsPanel = new JPanel();
        
        controlsPanel.add(new JLabel("Start Date (dd-MM-yyyy HH:mm):"));
        startDateField = new JTextField(12);
        controlsPanel.add(startDateField);
        
        controlsPanel.add(new JLabel("End Date:"));
        endDateField = new JTextField(12);
        controlsPanel.add(endDateField);
        
        chartTypeComboBox = new JComboBox<>(new String[]{"Pie Chart", "Bar Chart", "Line Chart"});
        controlsPanel.add(chartTypeComboBox);
        
        generateButton = new JButton("Generate Chart");
        controlsPanel.add(generateButton);
        
        add(controlsPanel, BorderLayout.NORTH);

        // Center panel for chart
        chartContainer = new JPanel();
        chartContainer.setLayout(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);
    }

    /**
     * @return the entered start date string (expected dd-MM-yyyy HH:mm format)
     */
    public String getStartDate() {
        return startDateField.getText().trim();
    }

    /**
     * @return the entered end date string
     */
    public String getEndDate() {
        return endDateField.getText().trim();
    }

    /**
     * @return the name of the selected chart type ("Pie Chart", "Bar Chart", or "Line Chart")
     */
    public String getSelectedChartType() {
        return (String) chartTypeComboBox.getSelectedItem();
    }

    /**
     * @return the JButton instance for triggering chart generation
     */
    public JButton getGenerateButton() {
        return generateButton;
    }

    /**
     * Replaces the currently displayed chart panel with the newly provided one.
     * 
     * @param chartPanel The generated XChartPanel to display
     */
    public void displayChart(JPanel chartPanel) {
        chartContainer.removeAll();
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
    
    /**
     * Displays an error message dialog.
     * 
     * @param message The error message to display
     */
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
