package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import model.CSVExporter;
import model.CSVImporter;
import model.ExpenseTrackerModel;
import model.InputValidation;
import model.Transaction;
import view.ExpenseTrackerView;

/**
 * Provides the application programming layer to support the
 * following interface: addTransaction, delete, import, export.
 * 
 * NOTE) Represents the Controller in the MVC architecture pattern.
 */
public class ExpenseTrackerController {
	private ExpenseTrackerModel model = new ExpenseTrackerModel();    
    private ExpenseTrackerView view = new ExpenseTrackerView(model);
    
    public ExpenseTrackerController() {
    	super();
    	
    	// Hook up the view and controller
    	
        // Handle add transaction button clicks
        view.getDataPanelView().getAddTransactionBtn().addActionListener(e -> {
        	addTransaction();
        });
        
        // Handle "Delete" menu item clicks
        view.getDeleteMenuItem().addActionListener(e -> {
        	delete();
        });
        
        // Handle "Open File..." menu item clicks
        view.getOpenFileMenuItem().addActionListener(e -> {
        	openFile();
        });
        
        // Handle "Save" menu item clicks
        view.getSaveAsMenuItem().addActionListener(e -> {	  
        	saveAs();
        });
        
        // Handle tab selection
        view.getTabbedPanel().addChangeListener(e -> {
            if (view.getTabbedPanel().getSelectedIndex() == 1) { // 1 = Analysis tab
                if (model.getTransactions().isEmpty()) {
                    view.displayErrorMessage("No transactions available. Please add transactions before viewing analysis.");
                }
            }
        });

        // Handle generate Chart button
        view.getAnalysisPanelView().getGenerateButton().addActionListener(e -> {
            if (model.getTransactions().isEmpty()) {
                view.displayErrorMessage("No transactions available. Please add transactions before viewing analysis.");
                return;
            }
            
            String startStr = view.getAnalysisPanelView().getStartDate();
            String endStr = view.getAnalysisPanelView().getEndDate();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            
            List<Transaction> filteredTransactions = new java.util.ArrayList<>();
            try {
                java.util.Date startDate = null;
                java.util.Date endDate = null;
                
                if (!startStr.isEmpty()) {
                    startDate = sdf.parse(startStr);
                }
                if (!endStr.isEmpty()) {
                    endDate = sdf.parse(endStr);
                }
                
                for (Transaction t : model.getTransactions()) {
                    java.util.Date tDate = sdf.parse(t.getTimestamp());
                    boolean afterStart = (startDate == null) || !tDate.before(startDate);
                    boolean beforeEnd = (endDate == null) || !tDate.after(endDate);
                    
                    if (afterStart && beforeEnd) {
                        filteredTransactions.add(t);
                    }
                }
                
            } catch (java.text.ParseException ex) {
                view.displayErrorMessage("Invalid date format. Please use dd-MM-yyyy HH:mm");
                return;
            }
            
            if (filteredTransactions.isEmpty()) {
                view.displayErrorMessage("No transactions found in this time window.");
                return;
            }
            
            // Strategy Pattern Application
            String chartType = view.getAnalysisPanelView().getSelectedChartType();
            view.ChartStrategy strategy;
            if ("Pie Chart".equals(chartType)) {
                strategy = new view.PieChartStrategy();
            } else {
                strategy = new view.BarChartStrategy();
            }
            
            javax.swing.JPanel chartPanel = strategy.buildChart(filteredTransactions, "Expenses");
            view.getAnalysisPanelView().displayChart(chartPanel);
        });
        
        
        // Initialize view
        view.setVisible(true);
    }
    
    public ExpenseTrackerModel getModel() {
    	// For testing purposes
    	return this.model;
    }
    
    public ExpenseTrackerView getView() {
    	// For testing purposes
    	return this.view;
    }
    
    public void addTransaction() { 
    	try {
    		// Get transaction data from view
    		double amount = view.getDataPanelView().getAmount(); 
    		String category = view.getDataPanelView().getCategory();

    		// Create transaction object
    		Transaction t = new Transaction(amount, category);

    		// Call controller to add transaction
    		model.addTransaction(t);
    		view.refresh();
    	}
    	catch (NumberFormatException nfe) {
    		view.displayErrorMessage("The amount cannot be parsed as a double number.");
    	}
    	catch (IllegalArgumentException iae) {
    		view.displayErrorMessage(iae.getMessage());
    	}
    }
    
    public void delete() {
        int selectedTransactionID = view.getDataPanelView().getSelectedTransactionID();
    	boolean removed = model.removeTransaction(selectedTransactionID);
    	if (! removed) {
    		view.displayErrorMessage("A valid transaction was not selected to be removed.");
    	}
    	else {
    		view.refresh();
    	}
    }
    
    public void openFile() {
    	String inputFileName = view.showFileChooser(true);
    	if (inputFileName != null) {  	    
    		int transactionCount = model.getTransactions().size();
    		for (int i = 0; i < transactionCount; i++) {
    			model.removeTransaction(0);
    		}

    		try {
    			CSVImporter csvImporter = new CSVImporter();
    			List<Transaction> importedTransactionsList = csvImporter.importTransactions(inputFileName);
    			for (Transaction importedTransaction : importedTransactionsList) {				
    				model.addTransaction(importedTransaction);
    			}
    		}
    		catch (IOException ioe) {
    			view.displayErrorMessage(ioe.getMessage());
    		}
    		view.refresh();
    	}
    }
    
    public void saveAs() {
    	String outputFileName = view.showFileChooser(false);
    	if (outputFileName != null) {
    		CSVExporter csvExporter = new CSVExporter();
    		String errorMessage = csvExporter.exportTransactions(model.getTransactions(), outputFileName);
    		if (errorMessage != null) {
    			view.displayErrorMessage(errorMessage);
    		}
    	}
    }
}
