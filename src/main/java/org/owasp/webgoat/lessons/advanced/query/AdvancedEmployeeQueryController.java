package org.owasp.webgoat.lessons.advanced.query;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(
    value = {
      "AdvancedQuery.hint.1",
      "AdvancedQuery.hint.2",
      "AdvancedQuery.hint.3"
    })
public class AdvancedEmployeeQueryController implements AssignmentEndpoint {

  private final SecurityFilterManager securityManager;

  public AdvancedEmployeeQueryController(SecurityFilterManager securityManager) {
    this.securityManager = securityManager;
  }

  @PostMapping("/AdvancedQuery/searchEmployee")
  @ResponseBody
  public AttackResult searchEmployee(@RequestParam String employee_name, @RequestParam String auth_token) {
    return performAdvancedSearch(employee_name, auth_token);
  }

  protected AttackResult performAdvancedSearch(String employee_name, String auth_token) {
    try {
      if (!securityManager.isRequestSecure(employee_name, auth_token)) {
        return failed(this).feedback("Request validation failed").build();
      }

      String result = securityManager.processEmployeeQuery(employee_name, auth_token);
      
      if (result.contains("Error")) {
        return failed(this).feedback("Search failed: " + result).build();
      }
      
      return success(this).feedback("advanced-query.success").output(result).build();
      
    } catch (Exception e) {
      return failed(this)
          .output("<br><span class='feedback-negative'>" + e.getMessage() + "</span>")
          .build();
    }
  }
}
