package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.model.Pessoa;

import java.util.List;

public interface PessoaService {
    List<Pessoa> buscarListaPessoas(PessoaFetchRequest pessoaFetchRequest);

    List<Pessoa> buscarPelaEscolaId(long escolaId);

    Pessoa buscarPeloId(long pessoaId);

    Pessoa salvarPessoa(PessoaCreateRequest pessoaCreateRequest);

    Pessoa alterarNomePessoa(long pessoaId, PessoaNomeChangeRequest pessoaNomeChangeRequest);

    Pessoa alterarCorpoPessoa(long pessoaId, PessoaCorpoChangeRequest pessoaCorpoChangeRequest);

    Pessoa alterarDataNascimento(long pessoaId, PessoaDataNascimentoChangeRequest pessoaDataNascimentoChangeRequest);

    Pessoa alterarSexoPessoa(long pessoaId, PessoaSexoChangeRequest pessoaSexoChangeRequest);

    void deletarPessoa(long pessoaId);
}
