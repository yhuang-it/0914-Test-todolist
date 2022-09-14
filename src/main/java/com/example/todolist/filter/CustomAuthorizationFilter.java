package com.example.todolist.filter;

import java.io.IOException;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Arrays.stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getServletPath().equals("/login") || request.getServletPath().equals("api/v1/registration")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length());
          Algorithm algorithm = Algorithm.HMAC256("IAmBatman!!".getBytes());

          JWTVerifier verifier = JWT.require(algorithm).build();
          DecodedJWT decodedJWT = verifier.verify(token);
          String username = decodedJWT.getSubject();
          String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
          Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
          stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
          });
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
              null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);
        } catch (Exception exception) {
          response.setHeader("error", exception.getMessage());
          response.sendError(HttpStatus.FORBIDDEN.value());
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }

  }
}
