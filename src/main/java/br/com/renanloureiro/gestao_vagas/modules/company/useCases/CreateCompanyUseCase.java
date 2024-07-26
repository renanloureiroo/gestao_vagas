package br.com.renanloureiro.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.exceptions.CompanyFoundException;
import br.com.renanloureiro.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository.findyByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail()).ifPresent((user) -> {
      throw new CompanyFoundException();
    });


    var passwordHashed = this.passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(passwordHashed);
    
    return this.companyRepository.save(companyEntity);
  }
}
