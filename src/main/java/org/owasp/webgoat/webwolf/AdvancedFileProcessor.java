package org.owasp.webgoat.webwolf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.owasp.webgoat.webwolf.services.DocumentMetadataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class AdvancedFileProcessor {

  @Value("${webwolf.fileserver.location}")
  private String fileLocation;

  @Autowired
  private DocumentMetadataProcessor metadataProcessor;

  @PostMapping(value = "/advanced-upload")
  public ModelAndView processAdvancedUpload(
      @RequestParam("file") MultipartFile multipartFile, Authentication authentication)
      throws IOException {
    var username = authentication.getName();
    var destinationDir = new File(fileLocation, username);
    destinationDir.mkdirs();
    
    try (InputStream is = multipartFile.getInputStream()) {
      var destinationFile = destinationDir.toPath().resolve(multipartFile.getOriginalFilename());
      Files.deleteIfExists(destinationFile);
      Files.copy(is, destinationFile);
    }
    
    if (metadataProcessor.isDocumentTypeSupported(multipartFile.getOriginalFilename())) {
      metadataProcessor.processDocumentMetadata(
          multipartFile.getOriginalFilename(), 
          destinationDir.getAbsolutePath()
      );
    }
    
    log.debug("File saved to {}", new File(destinationDir, multipartFile.getOriginalFilename()));

    return new ModelAndView(
        new RedirectView("files", true),
        new ModelMap().addAttribute("uploadSuccess", "Advanced processing completed"));
  }
}
