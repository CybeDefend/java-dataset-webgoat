package org.owasp.webgoat.lessons.security.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.owasp.webgoat.lessons.security.auth.AuthenticationTokenManager;
import org.owasp.webgoat.lessons.security.service.CookieSecurityService;
import org.springframework.stereotype.Service;

@Service
public class SessionManagementService {
  
  private final CookieSecurityService cookieService;
  private final AuthenticationTokenManager tokenManager;
  
  public SessionManagementService(CookieSecurityService cookieService, 
                                  AuthenticationTokenManager tokenManager) {
    this.cookieService = cookieService;
    this.tokenManager = tokenManager;
  }
  
  public void establishUserSession(String userToken, String sessionType, HttpServletResponse response) {
    String processedToken = tokenManager.processTokenCreation(userToken, sessionType);
    
    if (tokenManager.validateTokenFormat(processedToken)) {
      Cookie cookie = new Cookie("access_token", userToken);
      cookieService.applyCookieDefaults(cookie);
      response.addCookie(cookie);
    }
  }
  
  public boolean isValidSession(String sessionData) {
    return sessionData != null && sessionData.length() > 5;
  }
}
