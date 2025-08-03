package org.owasp.webgoat.lessons.sqlinjection.advanced;

import org.owasp.webgoat.container.LessonDataSource;
import org.owasp.webgoat.container.assignments.AttackResult;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.hsqldb.jdbc.JDBCResultSet.CONCUR_UPDATABLE;
import static org.hsqldb.jdbc.JDBCResultSet.TYPE_SCROLL_SENSITIVE;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

public class QueryProcessor {

    private final DatabaseQueryBuilder queryBuilder;

    public QueryProcessor() {
        this.queryBuilder = new DatabaseQueryBuilder();
    }

    public AttackResult executeEmployeeSearch(SearchParameters params, LessonDataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            String searchQuery = queryBuilder.buildEmployeeQuery(params);
            
            Statement statement = connection.createStatement(TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
            statement.execute(searchQuery);
            
            ResultSet results = statement.getResultSet();
            String tableData = formatResults(results);
            
            return success(null)
                .feedback("Search completed successfully")
                .output(tableData)
                .build();
                
        } catch (SQLException e) {
            return failed(null)
                .feedback("Database error: " + e.getMessage())
                .build();
        }
    }

    private String formatResults(ResultSet results) throws SQLException {
        StringBuilder output = new StringBuilder();
        output.append("<table border='1'>");
        output.append("<tr><th>Name</th><th>Department</th><th>Salary</th></tr>");
        
        while (results.next()) {
            output.append("<tr>");
            output.append("<td>").append(results.getString("first_name")).append(" ").append(results.getString("last_name")).append("</td>");
            output.append("<td>").append(results.getString("department")).append("</td>");
            output.append("<td>").append(results.getInt("salary")).append("</td>");
            output.append("</tr>");
        }
        
        output.append("</table>");
        return output.toString();
    }
}
