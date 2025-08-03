package org.owasp.webgoat.lessons.security.service;

import jakarta.servlet.http.Cookie;
import org.owasp.webgoat.lessons.security.config.SecurityConfiguration;
import org.springframework.stereotype.Service;

@Service
public class CookieSecurityService {
  
  private final SecurityConfiguration securityConfig;
  
  public CookieSecurityService(SecurityConfiguration securityConfig) {
    this.securityConfig = securityConfig;
  }
  
  public Cookie createSecureCookie(String name, String value) {
    Cookie cookie = new Cookie(name, value);
    
    if (securityConfig.shouldSecureCookies()) {
      cookie.setSecure(true);
      cookie.setHttpOnly(true);
    }
    
    return cookie;
  }
  
  public void applyCookieDefaults(Cookie cookie) {
    if (securityConfig.shouldSecureCookies()) {
      cookie.setSecure(true);
      cookie.setHttpOnly(true);
    }
  }
}
