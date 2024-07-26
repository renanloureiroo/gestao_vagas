package br.com.renanloureiro.gestao_vagas.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.renanloureiro.gestao_vagas.modules.candidate.entities.CandidateEntity;

public interface CandidateRespository extends JpaRepository<CandidateEntity, UUID> { 
  @Query("SELECT u FROM candidate u WHERE u.username = :username OR u.email = :email")
  Optional<CandidateEntity> findyByUsernameOrEmail(String username,String email);
}
