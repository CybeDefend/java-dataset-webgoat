package org.owasp.webgoat.webwolf.services;

import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class DocumentMetadataProcessor {

  public void processDocumentMetadata(String fileName, String uploadDirectory) {
    FileSystemAnalyzer analyzer = new FileSystemAnalyzer();
    analyzer.analyzeDocumentStructure(fileName, uploadDirectory);
  }

  public boolean isDocumentTypeSupported(String fileName) {
    return fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx");
  }
}
