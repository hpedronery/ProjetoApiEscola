package br.com.nova.api.escola.repositories.specifications;

import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.model.Cidade;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.stream.Stream;

public class CidadeSpecification implements Specification<Cidade> {

    private final transient CidadeFetchRequest cidadeFetchRequest;

    public CidadeSpecification(CidadeFetchRequest cidadeFetchRequest) {
        this.cidadeFetchRequest = cidadeFetchRequest;
    }


    @Override
    public Predicate toPredicate(Root<Cidade> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Condição nome igual
        Predicate nomePredicate = null;
        if (Objects.nonNull(cidadeFetchRequest.getNome())) {
            nomePredicate = builder.like(root.get("nome"), likeTwoWays(cidadeFetchRequest.getNome().toLowerCase()));
        }
        // Condição estado igual
        Predicate estadoPredicate = null;
        if (Objects.nonNull(cidadeFetchRequest.getEstado())) {
            estadoPredicate = builder.equal(root.get("estado"), cidadeFetchRequest.getEstado());
        }
        Predicate[] nonNullPredicates = Stream.of(nomePredicate, estadoPredicate).filter(Objects::nonNull).toArray(Predicate[]::new);
        return builder.and(nonNullPredicates);
    }

    private static String likeTwoWays(String str) {
        return "%" + str + "%";
    }
}
