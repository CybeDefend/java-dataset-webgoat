package org.owasp.webgoat.lessons.jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenContextManager {

  @Autowired
  private NetworkAccessValidator networkValidator;

  @Autowired 
  private JwtConfigurationResolver configResolver;

  public JwtProcessingContext createProcessingContext(String rawToken) {
    DecodedJWT decodedToken = JWT.decode(rawToken);
    return new JwtProcessingContext(decodedToken, networkValidator, configResolver);
  }

  public static class JwtProcessingContext {
    private final DecodedJWT token;
    private final NetworkAccessValidator validator;
    private final JwtConfigurationResolver configResolver;

    public JwtProcessingContext(DecodedJWT token, NetworkAccessValidator validator, 
                               JwtConfigurationResolver configResolver) {
      this.token = token;
      this.validator = validator;
      this.configResolver = configResolver;
    }

    public String resolveKeyProviderUrl() {
      var jkuClaim = token.getHeaderClaim("jku");
      String jkuUrl = jkuClaim.asString();
      
      if (configResolver.shouldValidateUrls() && !validator.isUrlSafe(jkuUrl)) {
        throw new SecurityException("JKU URL not in allowed domains");
      }
      
      return jkuUrl;
    }

    public DecodedJWT getToken() {
      return token;
    }
  }
}
