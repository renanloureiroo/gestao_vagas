package br.com.renanloureiro.gestao_vagas.modules.candidate.useCases;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.exceptions.CandidateFoundException;
import br.com.renanloureiro.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.renanloureiro.gestao_vagas.modules.candidate.repositories.CandidateRespository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRespository candidateRespository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRespository.findyByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user) -> {
      Logger.getLogger(CreateCandidateUseCase.class.toString()).log(Level.INFO,"Erro ao criar candidato, username ou email já cadastrado");
      throw new CandidateFoundException();
    });
    
    return this.candidateRespository.save(candidateEntity);
  }
}