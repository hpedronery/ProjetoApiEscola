package br.com.nova.api.escola.repositories.specifications;

import br.com.nova.api.escola.dtos.pessoa.PessoaFetchRequest;
import br.com.nova.api.escola.model.Pessoa;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.stream.Stream;

public class PessoaSpecification implements Specification<Pessoa> {

    private final transient PessoaFetchRequest pessoaFetchRequest;

    public PessoaSpecification(PessoaFetchRequest pessoaFetchRequest) {
        this.pessoaFetchRequest = pessoaFetchRequest;
    }

    @Override
    public Predicate toPredicate(Root<Pessoa> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Condição nome igual
        Predicate nomePredicate = null;
        if (Objects.nonNull(pessoaFetchRequest.getNome())) {
            nomePredicate = builder.like(root.get("nome"), likeTwoWays(pessoaFetchRequest.getNome().toLowerCase()));
        }
        // Condição sexo igual
        Predicate sexoPredicate = null;
        if (Objects.nonNull(pessoaFetchRequest.getSexo())) {
            sexoPredicate = builder.equal(root.get("sexo"), pessoaFetchRequest.getSexo());
        }
        // Condição corpo igual
        Predicate corpoPredicate = null;
        if (Objects.nonNull(pessoaFetchRequest.getCorpo())) {
            corpoPredicate = builder.equal(root.get("corpo"), pessoaFetchRequest.getCorpo());
        }
        Predicate[] nonNullPredicates = Stream.of(nomePredicate, sexoPredicate, corpoPredicate).filter(Objects::nonNull).toArray(Predicate[]::new);
        return builder.and(nonNullPredicates);
    }

    private static String likeTwoWays(String str) {
        return "%" + str + "%";
    }

}
