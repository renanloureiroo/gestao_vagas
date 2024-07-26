package br.com.renanloureiro.gestao_vagas.exceptions;

public class CandidateFoundException extends RuntimeException {
  public CandidateFoundException() {
    super("Candidate already exists");
  }
  
}
