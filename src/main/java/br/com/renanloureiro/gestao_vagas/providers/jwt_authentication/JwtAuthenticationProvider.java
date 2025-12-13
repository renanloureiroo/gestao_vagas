package br.com.renanloureiro.gestao_vagas.providers.jwt_authentication;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;

@Service
public class JwtAuthenticationProvider implements JwtAuthentication {

  @Value("${security.token.secret}")
  private String secretKey;
  @Value("${security.token.secret.candidate}")
  private String secretKeyCandidate;
  private final long DEFAULT_EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes

  @Override
  public String generateJWTToken(GenerateJwtTokenDTO generateJwtTokenDTO, ApplicationUsers applicationUsers) {
    long expirationTime = generateJwtTokenDTO.getExpirationTime();
    var secretKey = applicationUsers == ApplicationUsers.COMPANY ? this.secretKey : this.secretKeyCandidate;
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    if (expirationTime <= 0) {
      expirationTime = DEFAULT_EXPIRATION_TIME;
    }

    return JWT.create()
        .withSubject(generateJwtTokenDTO.getSubject())
        .withClaim("roles", generateJwtTokenDTO.getRoles())
        .withClaim("payload", generateJwtTokenDTO.getPayload())
        .withExpiresAt(Instant.now().plus(Duration.ofMillis(expirationTime)))
        .sign(algorithm);
  }

  @Override
  public DecodedJWT validateJWTToken(String token, ApplicationUsers applicationUsers) {
    try {
      token = token.replace("Bearer ", "");
      var secretKey = applicationUsers == ApplicationUsers.COMPANY ? this.secretKey : this.secretKeyCandidate;
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      var tokenDecoded = JWT.require(algorithm)
          .build()
          .verify(token);

      return tokenDecoded;
    } catch (JWTVerificationException exception) {
      exception.printStackTrace();
      return null;
    }
  }

}
