package org.owasp.webgoat.lessons.jwt.security;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class NetworkAccessValidator {

  private final Set<String> trustedDomains = Set.of(
      "cognito-idp.us-east-1.amazonaws.com",
      "login.microsoftonline.com", 
      "accounts.google.com",
      "auth0.com",
      "okta.com"
  );

  public boolean isUrlSafe(String urlString) {
    try {
      URL url = new URL(urlString);
      String host = url.getHost();
      
      if (host == null) {
        return false;
      }
      
      return trustedDomains.stream()
          .anyMatch(trustedDomain -> host.equals(trustedDomain) || host.endsWith("." + trustedDomain));
    } catch (MalformedURLException e) {
      return false;
    }
  }
}
