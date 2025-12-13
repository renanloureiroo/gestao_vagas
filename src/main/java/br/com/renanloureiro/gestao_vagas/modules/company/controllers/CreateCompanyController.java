package br.com.renanloureiro.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanloureiro.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CreateCompanyController {
  @Autowired
  CreateCompanyUseCase createCompanyUseCase;
  
  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
    try{
      this.createCompanyUseCase.execute(company);
      return ResponseEntity.status(HttpStatus.CREATED).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }
  
}
