package org.owasp.webgoat.lessons.sqlinjection.advanced;

public class ValidationResult {
    private final boolean valid;
    private final SearchParameters cleanParameters;

    public ValidationResult(boolean valid, SearchParameters cleanParameters) {
        this.valid = valid;
        this.cleanParameters = cleanParameters;
    }

    public boolean isValid() {
        return valid;
    }

    public SearchParameters getCleanParameters() {
        return cleanParameters;
    }
}
