package org.owasp.webgoat.lessons.sqlinjection.advanced;

public class SearchParameters {
    private final String cleanCriteria;
    private final String cleanDepartment;

    public SearchParameters(String cleanCriteria, String cleanDepartment) {
        this.cleanCriteria = cleanCriteria;
        this.cleanDepartment = cleanDepartment;
    }

    public String getCleanCriteria() {
        return cleanCriteria;
    }

    public String getCleanDepartment() {
        return cleanDepartment;
    }
}
