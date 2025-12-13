package br.com.renanloureiro.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;
import br.com.renanloureiro.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.renanloureiro.gestao_vagas.modules.candidate.dtos.AuthCandidateRequestDTO;
import br.com.renanloureiro.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import br.com.renanloureiro.gestao_vagas.modules.candidate.repositories.CandidateRespository;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.ApplicationUsers;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.JwtAuthentication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthCandidateUseCase {
  @Autowired
  private CandidateRespository candidateRespository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtAuthentication jwtAuthentication;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) {
    log.info("Authenticating candidate: {}", authCandidateRequestDTO.username());

    var candidate = this.candidateRespository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(() -> {
      throw new InvalidCredentialsException();
    });

    var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new InvalidCredentialsException();
    }

    var generateJwtTokenDTO = new GenerateJwtTokenDTO();
    var expirationTime = Instant.now().plus(Duration.ofMinutes(10)).toEpochMilli();
    generateJwtTokenDTO.setSubject(candidate.getId().toString());
    generateJwtTokenDTO.setExpirationTime(expirationTime);
    generateJwtTokenDTO.setRoles(Arrays.asList("CANDIDATE"));

    var accessToken = this.jwtAuthentication.generateJWTToken(generateJwtTokenDTO, ApplicationUsers.CANDIDATE);

    return AuthCandidateResponseDTO.builder()
        .access_token(accessToken)
        .expires_in(expirationTime)
        .build();
  }

}
