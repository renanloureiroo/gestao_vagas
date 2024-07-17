package br.com.renanloureiro.gestao_vagas.modules.candidate;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRespository extends JpaRepository<CandidateEntity, UUID> { 
}
