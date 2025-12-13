package br.com.renanloureiro.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.exceptions.CandidateNotFoundExeption;
import br.com.renanloureiro.gestao_vagas.modules.candidate.dtos.CandidateProfileDTO;
import br.com.renanloureiro.gestao_vagas.modules.candidate.repositories.CandidateRespository;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRespository candidateRespository;

  public CandidateProfileDTO execute(UUID idCandidate) {
    var candidate = this.candidateRespository.findById(idCandidate).orElseThrow(() -> {
      throw new CandidateNotFoundExeption();
    });

    CandidateProfileDTO response = CandidateProfileDTO.builder()
        .id(candidate.getId())
        .name(candidate.getName())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .description(candidate.getDescription())
        .build();

    return response;
  }
}
