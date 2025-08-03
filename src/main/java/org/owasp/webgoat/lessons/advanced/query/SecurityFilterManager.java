package org.owasp.webgoat.lessons.advanced.query;

import org.springframework.stereotype.Component;

@Component
public class SecurityFilterManager {
  
  private final EmployeeSearchService searchService;
  
  public SecurityFilterManager(EmployeeSearchService searchService) {
    this.searchService = searchService;
  }
  
  public String processEmployeeQuery(String userLastName, String userAuthCode) {
    if (!searchService.validateSearchCriteria(userLastName, userAuthCode)) {
      return "Invalid input detected";
    }
    
    return searchService.searchEmployeesByName(userLastName, userAuthCode);
  }
  
  public boolean isRequestSecure(String lastName, String authCode) {
    return lastName != null && authCode != null && 
           lastName.length() < 50 && authCode.length() < 20;
  }
}
