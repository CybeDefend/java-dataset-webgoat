package org.owasp.webgoat.webwolf.services;

import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class DocumentPathResolver {

  public String resolvePath(String fileName, String baseDirectory) {
    PathValidator validator = new PathValidator();
    String cleanedPath = validator.sanitizePath(fileName);
    return constructFullPath(cleanedPath, baseDirectory);
  }

  private String constructFullPath(String fileName, String baseDirectory) {
    return baseDirectory + File.separator + fileName;
  }
}
