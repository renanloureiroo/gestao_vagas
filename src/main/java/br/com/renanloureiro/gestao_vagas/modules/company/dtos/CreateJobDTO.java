package br.com.renanloureiro.gestao_vagas.modules.company.dtos;

import lombok.Data;

@Data
public class CreateJobDTO {
  private String beneficts;
  private String description;
  private String level;
}
