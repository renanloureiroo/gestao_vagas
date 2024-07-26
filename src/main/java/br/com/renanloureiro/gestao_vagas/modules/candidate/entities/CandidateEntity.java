package br.com.renanloureiro.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  
  @NotBlank(message = "O campo [username] é obrigatório")
  @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não pode conter espaços")
  private String username;

  @Email(message = "E-mail inválido")
  private String email;
  
  @Length(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
  private String password;
  private String description;
  private String curriculum;
  

  @CreationTimestamp()
  private LocalDateTime createdAt;
}
