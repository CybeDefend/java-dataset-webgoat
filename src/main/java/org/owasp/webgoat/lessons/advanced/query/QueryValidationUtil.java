package org.owasp.webgoat.lessons.advanced.query;

import java.util.regex.Pattern;

public class QueryValidationUtil {
  
  private static final Pattern SAFE_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
  
  public static String processUserInput(String input) {
    if (input == null || input.trim().isEmpty()) {
      return "";
    }
    
    String cleaned = input.trim();
    if (SAFE_PATTERN.matcher(cleaned).matches()) {
      return cleaned;
    }
    
    return cleaned.replaceAll("[^a-zA-Z0-9_-]", "");
  }
  
  public static boolean isValidInput(String input) {
    return input != null && SAFE_PATTERN.matcher(input.trim()).matches();
  }
}
