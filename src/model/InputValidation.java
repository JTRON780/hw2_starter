package model;

import java.util.Arrays;

/**
 * Utility class providing validation methods for transaction inputs and file names.
 */
public class InputValidation {
  /**
   * Array of valid transaction categories.
   */
  public static final String[] VALID_CATEGORIES = {"food", "travel", "bills", "entertainment", "other"};
	
  /**
   * Validates if the given transaction amount is within the acceptable range.
   * The amount must be strictly greater than 0 and less than or equal to 1000.
   *
   * @param amount the transaction amount to validate
   * @return true if the amount is valid, false otherwise
   */
  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  /**
   * Validates if the given transaction category is valid.
   * The category must not be null or empty, must contain only alphabetical characters,
   * and must be one of the pre-defined categories in {@link #VALID_CATEGORIES}.
   *
   * @param category the transaction category to validate
   * @return true if the category is valid, false otherwise
   */
  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    if(!Arrays.asList(VALID_CATEGORIES).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }

  /**
   * Validates if the given filename is valid for CSV operations.
   * The filename must not be null or empty, must not contain obvious path 
   * traversal patterns, must end with ".csv", and must have a valid base name.
   *
   * @param filename the filename to validate
   * @return true if the filename is valid, false otherwise
   */
  public static boolean isValidFilename(String filename) {
    if (filename == null) return false;
    String trimmed = filename.trim();
    if (trimmed.isEmpty()) return false;
    // Disallow obvious path traversal
    if (trimmed.contains("..")) return false;
    // Use the file name portion for checking the extension so absolute paths are allowed
    java.io.File f = new java.io.File(trimmed);
    String name = f.getName();
    if (name == null || name.trim().isEmpty()) return false;
    String lower = name.toLowerCase();
    if (!lower.endsWith(".csv")) return false;
    // Ensure there's at least one character before the extension
    if (name.length() <= 4) return false;
    return true;
  }
}