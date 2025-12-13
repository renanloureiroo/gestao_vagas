package br.com.renanloureiro.gestao_vagas.modules.candidate.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfileDTO {

  private UUID id;
  private String name;
  private String username;
  private String email;
  private String description;
}
