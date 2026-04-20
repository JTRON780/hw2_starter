package view;
import javax.swing.JPanel;
import java.util.List;
import model.Transaction;

public interface ChartStrategy {
    JPanel buildChart(List<Transaction> transactions, String title);
}
