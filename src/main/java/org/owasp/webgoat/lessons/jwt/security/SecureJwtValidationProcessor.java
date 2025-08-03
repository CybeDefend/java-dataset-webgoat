package org.owasp.webgoat.lessons.jwt.security;

import com.auth0.jwk.JwkProviderBuilder;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecureJwtValidationProcessor {

  @Autowired
  private TokenContextManager tokenManager;

  public URL processJkuUrl(String token) throws Exception {
    TokenContextManager.JwtProcessingContext context = tokenManager.createProcessingContext(token);
    String validatedUrl = context.resolveKeyProviderUrl();
    return new URL(validatedUrl);
  }

  public Object createKeyProvider(String token) throws Exception {
    URL secureUrl = processJkuUrl(token);
    var jwkProvider = new JwkProviderBuilder(secureUrl).build();
    return jwkProvider;
  }
}
