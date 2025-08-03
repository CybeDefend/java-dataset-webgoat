package org.owasp.webgoat.lessons.analytics.manager;

import org.owasp.webgoat.lessons.analytics.service.LogAnalyticsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityAnalyticsManager {
  
  private final LogAnalyticsService analyticsService;
  
  public SecurityAnalyticsManager(LogAnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
  }
  
  public String performSecureAnalysis(String userInput) {
    if (!analyticsService.validateSearchTerm(userInput)) {
      return "Invalid search term format";
    }
    
    return analyticsService.analyzeLogData(userInput);
  }
  
  public boolean isInputSafe(String input) {
    return input != null && input.length() < 100 && 
           !input.contains("'") && !input.contains(";");
  }
}
