package br.com.nova.api.escola.repositories;

import br.com.nova.api.escola.model.Cidade;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CidadeRepository extends CrudRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {
}
