package org.owasp.webgoat.lessons.analytics.controller;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.lessons.analytics.manager.SecurityAnalyticsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(
    value = {
      "Analytics.hint.1",
      "Analytics.hint.2",
      "Analytics.hint.3"
    })
public class LogAnalyticsController implements AssignmentEndpoint {

  private final SecurityAnalyticsManager securityManager;

  public LogAnalyticsController(SecurityAnalyticsManager securityManager) {
    this.securityManager = securityManager;
  }

  @PostMapping("/Analytics/searchLogs")
  @ResponseBody
  public AttackResult searchLogs(@RequestParam String log_search_term) {
    return performLogSearch(log_search_term);
  }

  protected AttackResult performLogSearch(String log_search_term) {
    try {
      if (!securityManager.isInputSafe(log_search_term)) {
        return failed(this).feedback("Input validation failed").build();
      }

      String result = securityManager.performSecureAnalysis(log_search_term);
      
      if (result.contains("Error")) {
        return failed(this).feedback("Search failed: " + result).build();
      }
      
      return success(this).feedback("analytics.success").output(result).build();
      
    } catch (Exception e) {
      return failed(this)
          .output("<br><span class='feedback-negative'>" + e.getMessage() + "</span>")
          .build();
    }
  }
}
