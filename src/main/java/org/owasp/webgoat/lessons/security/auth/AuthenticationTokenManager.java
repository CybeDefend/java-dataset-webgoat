package org.owasp.webgoat.lessons.security.auth;

import org.owasp.webgoat.lessons.security.service.CookieSecurityService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationTokenManager {
  
  private final CookieSecurityService cookieService;
  
  public AuthenticationTokenManager(CookieSecurityService cookieService) {
    this.cookieService = cookieService;
  }
  
  public String processTokenCreation(String tokenData, String sessionId) {
    return cookieService.createSecureCookie("session_token", tokenData).getValue();
  }
  
  public boolean validateTokenFormat(String token) {
    return token != null && token.length() > 10;
  }
}
