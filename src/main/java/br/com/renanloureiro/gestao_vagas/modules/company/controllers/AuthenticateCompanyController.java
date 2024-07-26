package br.com.renanloureiro.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.renanloureiro.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.renanloureiro.gestao_vagas.modules.company.dtos.AuthenticateCompanyDTO;
import br.com.renanloureiro.gestao_vagas.modules.company.useCases.AuthenticateCompanyUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/companies/session")
public class AuthenticateCompanyController {
  @Autowired
  private AuthenticateCompanyUseCase authenticateCompanyUseCase;

  @PostMapping()
  public ResponseEntity<Object> handle(@RequestBody AuthenticateCompanyDTO authenticateCompanyDTO) {
    
    try {
      var response = this.authenticateCompanyUseCase.execute(authenticateCompanyDTO);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (InvalidCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

  }
  
}
