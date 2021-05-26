package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.cidade.CidadeCreateRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeEstadoChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeNomeChangeRequest;
import br.com.nova.api.escola.model.Cidade;

import javax.validation.Valid;
import java.util.List;

public interface CidadeService {

    Cidade buscarPeloId(long cidadeId);

    List<Cidade> buscaListaDeCidades(CidadeFetchRequest cidadeFetchRequest);

    Cidade salvarCidade(@Valid CidadeCreateRequest cidadeCreateRequest);

    Cidade alterarNomeCidade(long cidadeId, CidadeNomeChangeRequest cidadeNomeChangeRequest);

    Cidade alterarEstadoCidade(long cidadeId, CidadeEstadoChangeRequest cidadeEstadoChangeRequest);

    void deletarCidade(long cidadeId);
}
