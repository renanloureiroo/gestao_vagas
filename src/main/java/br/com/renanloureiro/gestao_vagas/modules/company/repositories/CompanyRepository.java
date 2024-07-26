package br.com.renanloureiro.gestao_vagas.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import br.com.renanloureiro.gestao_vagas.modules.company.entities.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
  @Query("SELECT u FROM company u WHERE u.username = :username OR u.email = :email")
  Optional<CompanyEntity> findyByUsernameOrEmail(String username,String email);


  @Query("SELECT u FROM company u WHERE u.email = :email")
  Optional<CompanyEntity> findByEmail(String email);


  @Query("SELECT u FROM company u WHERE u.username = :username")
  Optional<CompanyEntity> findByUsername(String username);
}

