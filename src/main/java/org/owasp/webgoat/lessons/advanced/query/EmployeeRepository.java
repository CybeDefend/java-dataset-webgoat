package org.owasp.webgoat.lessons.advanced.query;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import org.owasp.webgoat.container.LessonDataSource;
import org.springframework.stereotype.Repository;
import static org.hsqldb.jdbc.JDBCResultSet.CONCUR_UPDATABLE;
import static org.hsqldb.jdbc.JDBCResultSet.TYPE_SCROLL_SENSITIVE;

@Repository
public class EmployeeRepository {
  
  private final LessonDataSource dataSource;
  
  public EmployeeRepository(LessonDataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public String findByNameAndAuth(String lastName, String authCode) {
    String queryInjection = "SELECT * FROM employees WHERE last_name = '" 
        + lastName + "' AND auth_tan = '" + authCode + "'";
    
    try (Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement(TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
      statement.execute(queryInjection);
      return "Query executed successfully";
    } catch (SQLException e) {
      return "Error: " + e.getMessage();
    }
  }
}
