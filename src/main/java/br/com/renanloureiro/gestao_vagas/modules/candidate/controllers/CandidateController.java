package br.com.renanloureiro.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanloureiro.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.renanloureiro.gestao_vagas.modules.candidate.CandidateRespository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
  @Autowired
  private CandidateRespository candidateRespository;

  @PostMapping()
  public CandidateEntity create(@Valid @RequestBody CandidateEntity candidate) {
    return this.candidateRespository.save(candidate);
  }

  

}
