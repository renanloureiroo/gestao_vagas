package br.com.renanloureiro.gestao_vagas.providers.jwt_authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;

public interface JwtAuthentication {
  String generateJWTToken(GenerateJwtTokenDTO generateJwtTokenDTO, ApplicationUsers applicationUsers);

  DecodedJWT validateJWTToken(String token, ApplicationUsers applicationUsers);

}
