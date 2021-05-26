package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.escola.EscolaCreateRequest;
import br.com.nova.api.escola.dtos.escola.EscolaFetchRequest;
import br.com.nova.api.escola.dtos.escola.EscolaNomeChangeRequest;
import br.com.nova.api.escola.dtos.escola.EscolaRedeChangeRequest;
import br.com.nova.api.escola.model.Escola;

import javax.validation.Valid;
import java.util.List;

public interface EscolaService {
    List<Escola> buscaListaEscolasPeloNomeOuRedeEscolar(EscolaFetchRequest escolaFetchRequest);

    List<Escola> buscaEscolaPelaCidadeId(long cidadeId);

    Escola buscaEscolaPeloId(long escolaId);

    Escola criaEscola(@Valid EscolaCreateRequest escolaCreateRequest);

    Escola alteraNomeEscola(long escolaId, EscolaNomeChangeRequest escolaNomeChangeRequest);

    Escola alteraRedeEscola(long escolaId, EscolaRedeChangeRequest escolaRedeChangeRequest);

    Escola alteraCidadeEscola(long escolaId, long cidadeId);

    void deletaEscola(long escolaId);
}
