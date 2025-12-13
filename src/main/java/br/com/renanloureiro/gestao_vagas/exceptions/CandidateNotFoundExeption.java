package br.com.renanloureiro.gestao_vagas.exceptions;

public class CandidateNotFoundExeption extends RuntimeException {
  public CandidateNotFoundExeption() {
    super("Candidate not found");
  }
}
