package br.com.renanloureiro.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.ApplicationUsers;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.JwtAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  @Autowired
  private JwtAuthentication jwtAuthentication;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(null);
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      var subject = this.jwtAuthentication.validateJWTToken(authorizationHeader, ApplicationUsers.COMPANY);

      if (subject.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      request.setAttribute("company_id", subject);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null,
          Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);

  }

}
