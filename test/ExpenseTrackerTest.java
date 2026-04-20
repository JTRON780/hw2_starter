

import static org.junit.Assert.assertEquals;

import javax.swing.table.DefaultTableModel;
import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.InputValidation;
import model.Transaction;
import view.DataPanelView;
import view.ExpenseTrackerView;

public class ExpenseTrackerTest {

  // For unit testing
  private ExpenseTrackerModel model;
  // For end-to-end testing
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
	model = new ExpenseTrackerModel();
	controller = new ExpenseTrackerController();
  }
  
  @Test
  public void testInitialConfiguration() {
    // There aren't any pre-conditions to be checked
    // The setup method called the constructors
    // Check the post-conditions
    assertEquals(0, model.getTransactions().size());
  }

  private void testAddTransactionHelper(double amount, String category) {
	    // Check the pre-conditions
	    assertEquals(0, model.getTransactions().size());
		
	    // Create a new transaction and add it
	    Transaction transaction = new Transaction(amount, category);
	    model.addTransaction(transaction);

	    // Check the post-conditions: 
	    // Verify that the transaction was added appropriately
	    java.util.List<Transaction> transactions = model.getTransactions();
	    assertEquals(1, transactions.size());
	    assertEquals(amount, transactions.get(0).getAmount(), 0.001);
	    assertEquals(category, transactions.get(0).getCategory());
	    assertEquals(amount, model.computeTransactionsTotalCost(), 0.001);
  }
  
  @Test
  public void testAddTransaction() {
	  double amount = 100.0;
	  String category = "Food";
	  this.testAddTransactionHelper(amount, category);
  }
  
  @Test
  public void testRemoveTransaction() {
	  // Initialize: Add a new transaction
	  double amount = 100.0;
	  String category = "Food";
	  this.testAddTransactionHelper(amount, category);
	  // Remove that transaction
	  model.removeTransaction(0);
	  // Check the post-conditions
	  assertEquals(0, model.getTransactions().size());
	  assertEquals(0, model.computeTransactionsTotalCost(), 0.001);
  }
  
  @Test
  public void testAddTransactionE2E() {
	  // Perform initialization and check the preconditions
	  double newAmount = 44.0;
	  String newCategory = "Other";
	  DataPanelView view = controller.getView().getDataPanelView();
	  view.setAmount("" + newAmount);
	  view.setCategory(newCategory);
	  assertEquals(0, view.getTransactionsTableRowCount());
	  // Call the unit under test: Add the new transaction
	  view.getAddTransactionBtn().doClick();
	  // Check the post-conditions
	  assertEquals(2, view.getTransactionsTableRowCount());
	  assertEquals(newAmount, view.getTransactionsTableValueAt(0, 1));
	  assertEquals(newCategory, view.getTransactionsTableValueAt(0, 2));
	  assertEquals(newAmount, view.getTransactionsTableValueAt(1, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTransactionInvalidAmount() {
	  // Pre-conditions
	  assertEquals(0, model.getTransactions().size());
	  
	  // Call unit under test (should throw exception)
	  Transaction t = new Transaction(-50.0, "food");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTransactionInvalidCategory() {
	  // Pre-conditions
	  assertEquals(0, model.getTransactions().size());
	  
	  // Call unit under test (should throw exception)
	  Transaction t = new Transaction(50.0, "InvalidCategory");
  }

  @Test
  public void testRemoveTransactionInvalidIndex() {
	  // Pre-conditions
	  assertEquals(0, model.getTransactions().size());
	  
	  // Call unit under test
	  boolean removed = model.removeTransaction(0); // Removing from empty list
	  
	  // Post-conditions
	  assertEquals(false, removed);
	  assertEquals(0, model.getTransactions().size());
  }

  @Test
  public void testInputValidationEdgeCases() {
      // Pre-conditions
      // (no explicit pre-conditions needed for statics)
      
      // Call unit under test
      boolean invalidAmountZero = InputValidation.isValidAmount(0.0);
      boolean invalidAmountHigh = InputValidation.isValidAmount(1001.0);
      boolean validCategoryCaps = InputValidation.isValidCategory("FOOD");
      
      
      // Post-conditions
      assertEquals(false, invalidAmountZero);
      assertEquals(false, invalidAmountHigh);
      assertEquals(true, validCategoryCaps);
  }

  @Test
  public void testRemoveTransactionNegativeIndex() {
      // Pre-conditions
      assertEquals(0, model.getTransactions().size());
      
      // Call unit under test
      boolean removed = model.removeTransaction(-1);
      
      // Post-conditions
      assertEquals(false, removed);
  }

  @Test
  public void testTransactionGetters() {
      // Call unit under test
      Transaction t = new Transaction(50.0, "travel");
      
      // Post-conditions
      assertEquals(50.0, t.getAmount(), 0.001);
      assertEquals("travel", t.getCategory());
      // we can't assert exact timestamp safely due to ms diff, but we can assert it's not null
      assertEquals(true, t.getTimestamp() != null && !t.getTimestamp().isEmpty());
  }

  @Test
  public void testTransactionPackageConstructor() throws Exception {
      // Use reflection to access the package-private constructor
      java.lang.reflect.Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor(double.class, String.class, String.class);
      constructor.setAccessible(true);
      Transaction t = constructor.newInstance(50.0, "travel", "15-05-2026 12:00");
      
      // Post-conditions
      assertEquals(50.0, t.getAmount(), 0.001);
      assertEquals("travel", t.getCategory());
      assertEquals("15-05-2026 12:00", t.getTimestamp());
  }
}