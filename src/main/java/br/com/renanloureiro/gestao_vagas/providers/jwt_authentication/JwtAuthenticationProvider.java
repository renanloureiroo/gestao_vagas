package br.com.renanloureiro.gestao_vagas.providers.jwt_authentication;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;

@Component
public class JwtAuthenticationProvider implements JwtAuthentication {
  
  private final String secretKey = "secret";
  private final long DEFAULT_EXPIRATION_TIME =  1000 * 60 * 15; // 15 minutes
  

  @Override
  public String generateJWTToken(GenerateJwtTokenDTO generateJwtTokenDTO) {
    long expirationTime = generateJwtTokenDTO.getExpirationTime();
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    if(expirationTime <= 0) {
      expirationTime = DEFAULT_EXPIRATION_TIME;
    }

    return JWT.create()
      .withSubject(generateJwtTokenDTO.getSubject())
      .withClaim("payload", generateJwtTokenDTO.getPayload())
      .withExpiresAt(new java.util.Date(System.currentTimeMillis() + expirationTime))
      .sign(algorithm);
  }

  @Override
  public boolean validateJWTToken(String token) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secretKey); 
        JWTVerifier verifier = JWT.require(algorithm)     
                .build();
        verifier.verify(token);
        return true; 
    } catch (JWTVerificationException exception){
        return false;
    }
  }
  
}
