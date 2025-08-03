package org.owasp.webgoat.lessons.sqlinjection.advanced;

import org.owasp.webgoat.container.LessonDataSource;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.stereotype.Service;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;

@Service
public class EmployeeSearchService {

    private final InputValidator validator;
    private final QueryProcessor queryProcessor;

    public EmployeeSearchService() {
        this.validator = new InputValidator();
        this.queryProcessor = new QueryProcessor();
    }

    public AttackResult processSearchRequest(EmployeeSearchRequest request, LessonDataSource dataSource) {
        try {
            ValidationResult validationResult = validator.validateSearchInput(request);
            if (validationResult.isValid()) {
                SearchParameters cleanParams = validationResult.getCleanParameters();
                return queryProcessor.executeEmployeeSearch(cleanParams, dataSource);
            } else {
                return failed(null)
                    .feedback("Invalid search parameters")
                    .build();
            }
        } catch (Exception e) {
            return failed(null)
                .feedback("Search failed: " + e.getMessage())
                .build();
        }
    }
}
