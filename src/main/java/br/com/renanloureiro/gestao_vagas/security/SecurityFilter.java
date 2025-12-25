package br.com.renanloureiro.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    // SecurityContextHolder.getContext().setAuthentication(null);
    String authorizationHeader = request.getHeader("Authorization");

    if (request.getRequestURI().startsWith("/companies")) {
      if (authorizationHeader != null) {
        var tokenDecoded = this.jwtAuthentication.validateJWTToken(authorizationHeader, ApplicationUsers.COMPANY);

        if (tokenDecoded == null) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }

        request.setAttribute("company_id", tokenDecoded.getSubject());
        var roles = tokenDecoded.getClaim("roles").asList(String.class);
        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .toList();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(),
            null,
            grants);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    filterChain.doFilter(request, response);

  }

}
