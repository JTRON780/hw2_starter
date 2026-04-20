package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Transaction;

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
        
        chartTypeComboBox = new JComboBox<>(new String[]{"Pie Chart", "Bar Chart"});
        controlsPanel.add(chartTypeComboBox);
        
        generateButton = new JButton("Generate Chart");
        controlsPanel.add(generateButton);
        
        add(controlsPanel, BorderLayout.NORTH);

        // Center panel for chart
        chartContainer = new JPanel();
        chartContainer.setLayout(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);
    }

    public String getStartDate() {
        return startDateField.getText().trim();
    }

    public String getEndDate() {
        return endDateField.getText().trim();
    }

    public String getSelectedChartType() {
        return (String) chartTypeComboBox.getSelectedItem();
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void displayChart(JPanel chartPanel) {
        chartContainer.removeAll();
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
    
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
