package org.owasp.webgoat.lessons.sqlinjection.advanced;

import org.owasp.webgoat.container.LessonDataSource;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;

@RestController
public class EmployeeSearchController implements AssignmentEndpoint {

    private final LessonDataSource dataSource;
    private final EmployeeSearchService searchService;

    public EmployeeSearchController(LessonDataSource dataSource, EmployeeSearchService searchService) {
        this.dataSource = dataSource;
        this.searchService = searchService;
    }

    @PostMapping("/advanced/employee-search")
    @ResponseBody
    public AttackResult searchEmployees(@RequestParam String criteria, @RequestParam String department) {
        try {
            EmployeeSearchRequest request = new EmployeeSearchRequest(criteria, department);
            return searchService.processSearchRequest(request, dataSource);
        } catch (Exception e) {
            return failed(this).feedback("Error processing search: " + e.getMessage()).build();
        }
    }
}
