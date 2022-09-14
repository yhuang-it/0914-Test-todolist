package com.example.todolist.filter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.todolist.entity.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
  
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    log.info("username is {}", username);
    log.info("password is {}", password);

    UsernamePasswordAuthenticationToken authenticationToken = 
      new UsernamePasswordAuthenticationToken(username, password);

    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    
    User user = (User)authResult.getPrincipal();

    Algorithm algorithm = Algorithm.HMAC256("IAmBatman!!".getBytes());

    String token = JWT.create()
      .withSubject(user.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
      .withIssuer(request.getRequestURL().toString())
      .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
      .sign(algorithm);
    
    response.setHeader("access_token", token);
  }   
}
