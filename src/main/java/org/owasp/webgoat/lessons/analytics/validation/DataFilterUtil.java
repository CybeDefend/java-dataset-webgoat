package org.owasp.webgoat.lessons.analytics.validation;

import java.util.regex.Pattern;

public class DataFilterUtil {
  
  private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-\\s]+$");
  
  public static String cleanLogData(String input) {
    if (input == null || input.trim().isEmpty()) {
      return "";
    }
    
    String trimmed = input.trim();
    if (ALPHANUMERIC_PATTERN.matcher(trimmed).matches()) {
      return trimmed;
    }
    
    return trimmed.replaceAll("[^a-zA-Z0-9_\\-\\s]", "");
  }
  
  public static boolean isValidLogInput(String input) {
    return input != null && ALPHANUMERIC_PATTERN.matcher(input.trim()).matches();
  }
}
