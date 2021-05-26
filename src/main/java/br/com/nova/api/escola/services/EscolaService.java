package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.escola.EscolaCreateRequest;
import br.com.nova.api.escola.dtos.escola.EscolaFetchRequest;
import br.com.nova.api.escola.dtos.escola.EscolaNomeChangeRequest;
import br.com.nova.api.escola.dtos.escola.EscolaRedeChangeRequest;
import br.com.nova.api.escola.model.Escola;

import javax.validation.Valid;
import java.util.List;

public interface EscolaService {
    List<Escola> buscaListaEscolas(EscolaFetchRequest escolaFetchRequest);

    List<Escola> buscarPelaCidadeId(long cidadeId);

    Escola buscarPeloId(long escolaId);

    Escola salvarEscola(@Valid EscolaCreateRequest escolaCreateRequest);

    Escola alterarNomeEscola(long escolaId, EscolaNomeChangeRequest escolaNomeChangeRequest);

    Escola alterarRedeEscola(long escolaId, EscolaRedeChangeRequest escolaRedeChangeRequest);

    Escola alterarCidadeEscola(long escolaId, long cidadeId);

    void deletarEscola(long escolaId);
}
