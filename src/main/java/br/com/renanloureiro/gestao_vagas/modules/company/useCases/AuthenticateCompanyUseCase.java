package br.com.renanloureiro.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;
import br.com.renanloureiro.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.renanloureiro.gestao_vagas.modules.company.dtos.AuthCompanyResponseDTO;
import br.com.renanloureiro.gestao_vagas.modules.company.dtos.AuthenticateCompanyDTO;
import br.com.renanloureiro.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.ApplicationUsers;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.JwtAuthentication;

@Service
public class AuthenticateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private JwtAuthentication jwtAuthentication;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCompanyResponseDTO execute(AuthenticateCompanyDTO authenticateCompanyDTO) {
    CompanyEntity company = this.validateCredentials(authenticateCompanyDTO);

    GenerateJwtTokenDTO generateJwtTokenDTO = new GenerateJwtTokenDTO();
    var expirationTime = Instant.now().plus(Duration.ofMinutes(10)).toEpochMilli();
    generateJwtTokenDTO.setSubject(company.getId().toString());
    generateJwtTokenDTO.setRoles(Arrays.asList("COMPANY"));
    generateJwtTokenDTO.setExpirationTime(expirationTime);

    AuthCompanyResponseDTO response = AuthCompanyResponseDTO.builder()
        .access_token(jwtAuthentication.generateJWTToken(generateJwtTokenDTO, ApplicationUsers.COMPANY))
        .expires_in(expirationTime)
        .build();

    return response;
  }

  private CompanyEntity validateCredentials(AuthenticateCompanyDTO authenticateCompanyDTO) {
    return companyRepository.findByUsername(authenticateCompanyDTO.getUsername())
        .filter(company -> this.passwordEncoder.matches(authenticateCompanyDTO.getPassword(), company.getPassword()))
        .orElseThrow(InvalidCredentialsException::new);
  }
}