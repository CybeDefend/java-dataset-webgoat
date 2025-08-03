package org.owasp.webgoat.lessons.jwt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurationResolver {
  
  @Value("${webgoat.jwt.validate-urls:true}")
  private boolean validateUrls;
  
  @Value("${webgoat.jwt.strict-mode:false}")
  private boolean strictMode;

  public boolean shouldValidateUrls() {
    return validateUrls;
  }

  public boolean isStrictMode() {
    return strictMode;
  }
}
