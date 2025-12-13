package br.com.renanloureiro.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanloureiro.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.renanloureiro.gestao_vagas.modules.company.dtos.AuthenticateCompanyDTO;
import br.com.renanloureiro.gestao_vagas.modules.company.useCases.AuthenticateCompanyUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/companies")
public class AuthenticateCompanyController {
  @Autowired
  private AuthenticateCompanyUseCase authenticateCompanyUseCase;

  @PostMapping("/auth")
  public ResponseEntity<Object> handle(@RequestBody AuthenticateCompanyDTO authenticateCompanyDTO) {
    
    try {
      var response = this.authenticateCompanyUseCase.execute(authenticateCompanyDTO);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (InvalidCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

  }
  
}
