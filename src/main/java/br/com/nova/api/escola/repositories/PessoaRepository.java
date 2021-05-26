package br.com.nova.api.escola.repositories;

import br.com.nova.api.escola.model.Pessoa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends CrudRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {
    Optional<List<Pessoa>> findByEscolaId(long escolaId);
}
