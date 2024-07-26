package br.com.renanloureiro.gestao_vagas.modules.company.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticateCompanyDTO {
  private String username;
  private String password;
}
