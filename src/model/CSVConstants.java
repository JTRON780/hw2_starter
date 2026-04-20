package model;

/**
 * Interface containing constants used for CSV export and import operations.
 */
public interface CSVConstants {
	  public static final String COMMA_SEPARATOR = ",";
	  public static final String CSV_HEADERS = "Amount" + COMMA_SEPARATOR + "Category" + COMMA_SEPARATOR + "Date";
	  public static final String TRANSACTION_LIST_ERROR_MESSAGE = "The transaction list must be non-null and not empty.";
	  public static final String FILENAME_ERROR_MESSAGE = "Filename must be non-empty and end with .csv";
}
