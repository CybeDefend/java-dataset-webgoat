package org.owasp.webgoat.webwolf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.plugins.LessonTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AdvancedFileProcessorTest extends LessonTest {

  @Test
  @DisplayName("Advanced upload should process files correctly")
  void shouldProcessAdvancedUpload() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    
    MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());

    mockMvc.perform(multipart("/advanced-upload").file(file))
           .andExpect(status().is3xxRedirection());
  }

  @Test
  @DisplayName("Advanced upload should handle path traversal filenames")
  void shouldHandlePathTraversalFilenames() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    
    MockMultipartFile file = new MockMultipartFile("file", "../../../etc/passwd.pdf", "application/pdf", "malicious content".getBytes());

    mockMvc.perform(multipart("/advanced-upload").file(file))
           .andExpected(status().is3xxRedirection());
  }
}
