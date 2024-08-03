package br.com.renanloureiro.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf ->  csrf.disable())
    .authorizeHttpRequests(auth -> {
      auth.requestMatchers("/candidates")
      .permitAll()
      .requestMatchers("/companies")
      .permitAll();
      auth.requestMatchers("/companies/session")
      .permitAll();
      auth.anyRequest().authenticated();
    })
    .addFilterBefore(this.securityFilter, BasicAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
