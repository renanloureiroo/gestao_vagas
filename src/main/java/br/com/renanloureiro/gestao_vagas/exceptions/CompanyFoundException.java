package br.com.renanloureiro.gestao_vagas.exceptions;

public class CompanyFoundException extends RuntimeException {
  public CompanyFoundException() {
    super("Company already exists");
  }
  
}
