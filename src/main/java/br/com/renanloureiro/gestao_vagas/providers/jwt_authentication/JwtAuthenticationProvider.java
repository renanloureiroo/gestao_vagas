package br.com.renanloureiro.gestao_vagas.providers.jwt_authentication;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;

@Service
public class JwtAuthenticationProvider implements JwtAuthentication {
  
  @Value("${security.token.secret}")
  private String secretKey;
  private final long DEFAULT_EXPIRATION_TIME =  1000 * 60 * 15; // 15 minutes
  

  @Override
  public String generateJWTToken(GenerateJwtTokenDTO generateJwtTokenDTO) {
    long expirationTime = generateJwtTokenDTO.getExpirationTime();
    Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

    if(expirationTime <= 0) {
      expirationTime = DEFAULT_EXPIRATION_TIME;
    }

    return JWT.create()
      .withSubject(generateJwtTokenDTO.getSubject())
      .withClaim("payload", generateJwtTokenDTO.getPayload())
      .withExpiresAt(Instant.now().plus(Duration.ofMillis(expirationTime)))
      .sign(algorithm);
  }

  @Override
  public String validateJWTToken(String token) {
    try {
        token = token.replace("Bearer ", ""); 
        Algorithm algorithm = Algorithm.HMAC256(secretKey); 
        var subject = JWT.require(algorithm)     
                .build()
                .verify(token)
                .getSubject();
        return subject;
    } catch (JWTVerificationException exception){
        return "";
    }
  }
  
}
