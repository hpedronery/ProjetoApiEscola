package br.com.nova.api.escola.services;

import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.model.Pessoa;

import java.util.List;

public interface PessoaService {
    List<Pessoa> buscaListaPessoas(PessoaFetchRequest pessoaFetchRequest);

    List<Pessoa> buscaPessoaPelaEscolaId(long escolaId);

    Pessoa buscaPessoaPeloId(long pessoaId);

    Pessoa criaPessoa(PessoaCreateRequest pessoaCreateRequest);

    Pessoa alteraNomePessoa(long pessoaId, PessoaNomeChangeRequest pessoaNomeChangeRequest);

    Pessoa alteraCorpoEscolarPessoa(long pessoaId, PessoaCorpoChangeRequest pessoaCorpoChangeRequest);

    Pessoa alteraDataNascimentoPessoa(long pessoaId, PessoaDataNascimentoChangeRequest pessoaDataNascimentoChangeRequest);

    Pessoa alteraSexoPessoa(long pessoaId, PessoaSexoChangeRequest pessoaSexoChangeRequest);

    void deletaPessoa(long pessoaId);
}
