package br.com.nova.api.escola.repositories.specifications;

import br.com.nova.api.escola.dtos.escola.EscolaFetchRequest;
import br.com.nova.api.escola.model.Escola;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.stream.Stream;

public class EscolaSpecification implements Specification<Escola> {

    private final transient EscolaFetchRequest escolaFetchRequest;

    public EscolaSpecification(EscolaFetchRequest escolaFetchRequest) {
        this.escolaFetchRequest = escolaFetchRequest;
    }

    @Override
    public Predicate toPredicate(Root<Escola> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Condição nome igual
        Predicate nomePredicate = null;
        if (Objects.nonNull(escolaFetchRequest.getNome())) {
            nomePredicate = builder.like(root.get("nome"), likeTwoWays(escolaFetchRequest.getNome().toLowerCase()));
        }
        // Condição rede igual
        Predicate redeEscolaPredicate = null;
        if (Objects.nonNull(escolaFetchRequest.getRedeEscola())) {
            redeEscolaPredicate = builder.equal(root.get("redeEscola"), escolaFetchRequest.getRedeEscola());
        }
        Predicate[] nonNullPredicates = Stream.of(nomePredicate, redeEscolaPredicate).filter(Objects::nonNull).toArray(Predicate[]::new);
        return builder.and(nonNullPredicates);
    }

    private static String likeTwoWays(String str) {
        return "%" + str + "%";
    }
}
