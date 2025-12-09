package br.com.renanloureiro.gestao_vagas.exceptions;

public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException() {
    super("E-mail/username or password is incorrect");
  }
}
