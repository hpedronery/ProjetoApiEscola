package br.com.nova.api.escola.repositories;

import br.com.nova.api.escola.model.Escola;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EscolaRepository extends CrudRepository<Escola, Long>, JpaSpecificationExecutor<Escola> {
    List<Escola> findByCidadeId(long cidadeId);
}
