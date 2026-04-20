package view;
import javax.swing.JPanel;
import java.util.List;
import model.Transaction;

/**
 * Defines a contract for algorithm families that construct specific types of Chart panels.
 * Part of the Strategy Design Pattern.
 */
public interface ChartStrategy {
    /**
     * Builds and returns a JPanel containing a chart visualization of the given transactions.
     * 
     * @param transactions The list of filtered transactions to visualize
     * @param title The title of the chart
     * @return A JPanel containing the generated chart
     */
    JPanel buildChart(List<Transaction> transactions, String title);
}
