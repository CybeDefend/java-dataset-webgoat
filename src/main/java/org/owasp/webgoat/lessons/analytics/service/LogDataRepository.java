package org.owasp.webgoat.lessons.analytics.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.owasp.webgoat.container.LessonDataSource;
import org.springframework.stereotype.Repository;

@Repository
public class LogDataRepository {
  
  private final LessonDataSource dataSource;
  
  public LogDataRepository(LessonDataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public String searchLogEntries(String searchTerm) {
    String query = "SELECT * FROM access_log WHERE action LIKE '%" + searchTerm + "%'";
    
    try (Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet results = statement.executeQuery(query);
      
      if (results.next()) {
        return "Found log entries for: " + searchTerm;
      } else {
        return "No log entries found for: " + searchTerm;
      }
    } catch (SQLException e) {
      return "Error searching logs: " + e.getMessage();
    }
  }
}
