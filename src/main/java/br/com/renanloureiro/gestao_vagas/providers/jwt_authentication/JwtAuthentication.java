package br.com.renanloureiro.gestao_vagas.providers.jwt_authentication;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;

public interface JwtAuthentication {
  String generateJWTToken(GenerateJwtTokenDTO generateJwtTokenDTO, ApplicationUsers applicationUsers);

  String validateJWTToken(String token, ApplicationUsers applicationUsers);

}
