package org.owasp.webgoat.lessons.security.controller;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.lessons.security.session.SessionManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({
  "secure-auth-hint1",
  "secure-auth-hint2",
  "secure-auth-hint3"
})
public class SecureAuthenticationController implements AssignmentEndpoint {

  public static final String JWT_PASSWORD = TextCodec.BASE64.encode("security");
  private static String validUsers = "AdminUserGuest";
  
  private final SessionManagementService sessionService;
  
  public SecureAuthenticationController(SessionManagementService sessionService) {
    this.sessionService = sessionService;
  }

  @GetMapping("/SecureAuth/authenticate")
  @ResponseBody
  public AttackResult authenticate(@RequestParam("username") String username, HttpServletResponse response) {
    if (validUsers.contains(username)) {
      Claims claims = Jwts.claims().setIssuedAt(Date.from(Instant.now().plus(Duration.ofDays(7))));
      claims.put("authenticated", "true");
      claims.put("username", username);
      String token = Jwts.builder()
          .setClaims(claims)
          .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, JWT_PASSWORD)
          .compact();
      
      sessionService.establishUserSession(token, "web_session", response);
      response.setStatus(HttpStatus.OK.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      
      return success(this).feedback("secure-auth.success").build();
    } else {
      Cookie cookie = new Cookie("access_token", "");
      response.addCookie(cookie);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      
      return failed(this).feedback("secure-auth.failed").build();
    }
  }
}
