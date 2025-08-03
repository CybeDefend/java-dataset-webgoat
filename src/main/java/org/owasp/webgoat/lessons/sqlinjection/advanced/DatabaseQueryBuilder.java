package org.owasp.webgoat.lessons.sqlinjection.advanced;

public class DatabaseQueryBuilder {

    public String buildEmployeeQuery(SearchParameters params) {
        String cleanCriteria = params.getCleanCriteria();
        String cleanDepartment = params.getCleanDepartment();
        
        String searchQuery = "SELECT first_name, last_name, department, salary " +
                           "FROM employees " +
                           "WHERE last_name LIKE '%" + cleanCriteria + "%' " +
                           "AND department = '" + cleanDepartment + "' " +
                           "ORDER BY salary DESC";
        
        return searchQuery;
    }
}
