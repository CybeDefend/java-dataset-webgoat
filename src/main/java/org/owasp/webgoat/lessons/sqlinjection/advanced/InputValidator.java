package org.owasp.webgoat.lessons.sqlinjection.advanced;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern DANGEROUS_CHARS = Pattern.compile("[';\"\\-\\-/*\\*/xp_]", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_DEPARTMENT = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static final Pattern VALID_CRITERIA = Pattern.compile("^[a-zA-Z0-9\\s_.-]+$");

    public ValidationResult validateSearchInput(EmployeeSearchRequest request) {
        String criteria = request.getCriteria();
        String department = request.getDepartment();

        if (criteria == null || department == null) {
            return new ValidationResult(false, null);
        }

        String cleanCriteria = sanitizeInput(criteria.trim());
        String cleanDepartment = sanitizeInput(department.trim());

        if (isValidInput(cleanCriteria) && isValidInput(cleanDepartment)) {
            SearchParameters cleanParams = new SearchParameters(cleanCriteria, cleanDepartment);
            return new ValidationResult(true, cleanParams);
        }

        return new ValidationResult(false, null);
    }

    private String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        
        String sanitized = input.replaceAll(DANGEROUS_CHARS.pattern(), "");
        sanitized = sanitized.replace("'", "''");
        return sanitized;
    }

    private boolean isValidInput(String input) {
        return input != null && 
               input.length() > 0 && 
               input.length() <= 50 &&
               VALID_CRITERIA.matcher(input).matches() &&
               !DANGEROUS_CHARS.matcher(input).find();
    }
}
