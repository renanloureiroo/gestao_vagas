package br.com.renanloureiro.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanloureiro.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.renanloureiro.gestao_vagas.modules.company.entities.JobEntity;
import br.com.renanloureiro.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.renanloureiro.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity job) {

    var companyExists = this.companyRepository.findById(job.getCompanyId());

    if (companyExists.isEmpty()) {
      throw new CompanyNotFoundException();
    }

    return this.jobRepository.save(job);
  }
}
