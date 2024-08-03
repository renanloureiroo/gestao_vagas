package br.com.renanloureiro.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.dtos.GenerateJwtTokenDTO;
import br.com.renanloureiro.gestao_vagas.exceptions.InvalidCredentialsException;
import br.com.renanloureiro.gestao_vagas.modules.company.dtos.AuthenticateCompanyDTO;
import br.com.renanloureiro.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.renanloureiro.gestao_vagas.providers.jwt_authentication.JwtAuthentication;

@Service
public class AuthenticateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;
  
  @Autowired
  private JwtAuthentication jwtAuthentication;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthenticateCompanyDTO authenticateCompanyDTO) {
    CompanyEntity company = this.validateCredentials(authenticateCompanyDTO);

    GenerateJwtTokenDTO generateJwtTokenDTO = this.prepareJwtTokenDTO(company);
    
    return jwtAuthentication.generateJWTToken(generateJwtTokenDTO);
  }

  private CompanyEntity validateCredentials(AuthenticateCompanyDTO authenticateCompanyDTO) {
    return companyRepository.findByUsername(authenticateCompanyDTO.getUsername())
      .filter(company -> this.passwordEncoder.matches(authenticateCompanyDTO.getPassword(), company.getPassword()))
      .orElseThrow(InvalidCredentialsException::new);
  }

  private GenerateJwtTokenDTO prepareJwtTokenDTO(CompanyEntity company) {
    var generateJwtTokenDTO = new GenerateJwtTokenDTO();

    generateJwtTokenDTO.setSubject(company.getId().toString());
    generateJwtTokenDTO.setPayload(null);
    
    return generateJwtTokenDTO;
  }
}