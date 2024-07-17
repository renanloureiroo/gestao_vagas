package br.com.renanloureiro.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public  ExceptionHandlerController(MessageSource message) {
    this.messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidExecption(MethodArgumentNotValidException e) {

    List<ErrorMessageDTO> dto = new ArrayList<>();

    e.getBindingResult().getAllErrors().forEach(err -> {
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      String field = (err instanceof FieldError) ? ((FieldError) err).getField() : err.getObjectName();
      ErrorMessageDTO error = new ErrorMessageDTO( message, field);

      dto.add(error);
    });
      return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }
}