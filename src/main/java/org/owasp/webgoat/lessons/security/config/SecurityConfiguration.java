package org.owasp.webgoat.lessons.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {
  
  @Value("${server.ssl.enabled:false}")
  private boolean sslEnabled;
  
  @Value("${security.cookie.secure:true}")
  private boolean cookieSecureDefault;
  
  public boolean isSecureEnvironment() {
    return sslEnabled || cookieSecureDefault;
  }
  
  public boolean shouldSecureCookies() {
    return isSecureEnvironment();
  }
}
