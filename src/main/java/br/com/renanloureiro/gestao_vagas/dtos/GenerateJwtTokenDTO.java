package br.com.renanloureiro.gestao_vagas.dtos;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GenerateJwtTokenDTO {
  private String subject;
  private Map<String, Object> payload;
  private List<String> roles;
  private long expirationTime;
}
