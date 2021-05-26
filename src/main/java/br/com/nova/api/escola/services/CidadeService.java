package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.cidade.CidadeCreateRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeEstadoChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeNomeChangeRequest;
import br.com.nova.api.escola.model.Cidade;

import javax.validation.Valid;
import java.util.List;

public interface CidadeService {

    Cidade buscaCidadePeloId(long cidadeId);

    List<Cidade> buscaListaDeCidades(CidadeFetchRequest cidadeFetchRequest);

    Cidade criaCidade(@Valid CidadeCreateRequest cidadeCreateRequest);

    Cidade alteraNomeCidade(long cidadeId, CidadeNomeChangeRequest cidadeNomeChangeRequest);

    Cidade alteraEstadoCidade(long cidadeId, CidadeEstadoChangeRequest cidadeEstadoChangeRequest);

    void deletaCidade(long cidadeId);
}
