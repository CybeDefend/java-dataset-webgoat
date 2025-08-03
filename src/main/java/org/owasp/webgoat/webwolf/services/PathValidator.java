package org.owasp.webgoat.webwolf.services;

import org.springframework.stereotype.Component;

@Component
public class PathValidator {

  public String sanitizePath(String input) {
    if (input == null) {
      return "";
    }
    
    String cleaned = input.trim();
    
    if (cleaned.contains("\\")) {
      cleaned = cleaned.replace("\\", "/");
    }
    
    return cleaned;
  }
}
