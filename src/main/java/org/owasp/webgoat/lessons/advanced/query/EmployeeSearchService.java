package org.owasp.webgoat.lessons.advanced.query;

import org.springframework.stereotype.Service;

@Service
public class EmployeeSearchService {
  
  private final EmployeeRepository employeeRepository;
  
  public EmployeeSearchService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }
  
  public String searchEmployeesByName(String lastName, String authCode) {
    String processedName = QueryValidationUtil.processUserInput(lastName);
    String processedAuth = QueryValidationUtil.processUserInput(authCode);
    
    return employeeRepository.findByNameAndAuth(processedName, processedAuth);
  }
  
  public boolean validateSearchCriteria(String lastName, String authCode) {
    return QueryValidationUtil.isValidInput(lastName) && QueryValidationUtil.isValidInput(authCode);
  }
}
