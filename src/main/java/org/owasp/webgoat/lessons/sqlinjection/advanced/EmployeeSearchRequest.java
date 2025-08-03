package org.owasp.webgoat.lessons.sqlinjection.advanced;

public class EmployeeSearchRequest {
    private final String criteria;
    private final String department;

    public EmployeeSearchRequest(String criteria, String department) {
        this.criteria = criteria;
        this.department = department;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getDepartment() {
        return department;
    }
}
