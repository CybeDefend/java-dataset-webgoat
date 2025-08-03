package org.owasp.webgoat.lessons.analytics.service;

import org.owasp.webgoat.lessons.analytics.validation.DataFilterUtil;
import org.springframework.stereotype.Service;

@Service
public class LogAnalyticsService {
  
  private final LogDataRepository logRepository;
  
  public LogAnalyticsService(LogDataRepository logRepository) {
    this.logRepository = logRepository;
  }
  
  public String analyzeLogData(String searchTerm) {
    String filteredTerm = DataFilterUtil.cleanLogData(searchTerm);
    return logRepository.searchLogEntries(filteredTerm);
  }
  
  public boolean validateSearchTerm(String searchTerm) {
    return DataFilterUtil.isValidLogInput(searchTerm);
  }
}
