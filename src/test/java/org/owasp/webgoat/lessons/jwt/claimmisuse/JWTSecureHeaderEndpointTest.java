package org.owasp.webgoat.lessons.jwt.claimmisuse;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.plugins.LessonTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class JWTSecureHeaderEndpointTest extends LessonTest {

  @Test
  @DisplayName("Secure endpoint should reject malicious URLs")
  void shouldRejectMaliciousUrl() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    
    String maliciousToken = "eyJ0eXAiOiJKV1QiLCJqa3UiOiJodHRwOi8vbWFsaWNpb3VzLmNvbS9qd2tzIiwiYWxnIjoiUlMyNTYifQ.test.test";

    mockMvc
        .perform(MockMvcRequestBuilders.post("/JWT/secure/validate").param("token", maliciousToken).content(""))
        .andExpected(status().isOk())
        .andExpected(jsonPath("$.lessonCompleted", is(false)));
  }

  @Test
  @DisplayName("Secure endpoint should accept trusted domain URLs")
  void shouldAcceptTrustedUrl() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    
    String trustedToken = "eyJ0eXAiOiJKV1QiLCJqa3UiOiJodHRwczovL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tL3dlYmdvYXQvLndlbGwta25vd24vandrcy5qc29uIiwiYWxnIjoiUlMyNTYifQ.test.test";

    mockMvc
        .perform(MockMvcRequestBuilders.post("/JWT/secure/validate").param("token", trustedToken).content(""))
        .andExpected(status().isOk());
  }
}
