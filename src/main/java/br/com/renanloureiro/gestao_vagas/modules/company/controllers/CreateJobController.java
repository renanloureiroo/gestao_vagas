package br.com.renanloureiro.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanloureiro.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.renanloureiro.gestao_vagas.modules.company.entities.JobEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies/jobs")
public class CreateJobController {
  
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody JobEntity job) {
    try {
      var result = this.createJobUseCase.execute(job);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (CompanyNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
