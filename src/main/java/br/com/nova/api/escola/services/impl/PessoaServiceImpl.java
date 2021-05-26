package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Pessoa;
import br.com.nova.api.escola.repositories.EscolaRepository;
import br.com.nova.api.escola.repositories.PessoaRepository;
import br.com.nova.api.escola.repositories.specifications.PessoaSpecification;
import br.com.nova.api.escola.services.PessoaService;
import br.com.nova.api.escola.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EscolaRepository escolaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository, EscolaRepository escolaRepository, ModelMapper modelMapper) {
        this.pessoaRepository = pessoaRepository;
        this.escolaRepository = escolaRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Abaixo seguem metodos de busca, eles utilizam o identificador long quando se busca pelo id ou uma classe dto
     * quando se busca por algum argumento.
     *
     * @return retorna o(s) objeto(s) da busca.
     */

    @Override
    public List<Pessoa> buscaListaPessoas(PessoaFetchRequest pessoaFetchRequest) {
        PessoaSpecification pessoaSpecification = new PessoaSpecification(pessoaFetchRequest);
        return pessoaRepository.findAll(pessoaSpecification);
    }

    @Override
    public List<Pessoa> buscaPessoaPelaEscolaId(long escolaId) {
        List<Pessoa> pessoas = pessoaRepository.findByEscolaId(escolaId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("pessoa.escola.nao.encontrada"), escolaId));
        return pessoas;
    }

    @Override
    public Pessoa buscaPessoaPeloId(long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("pessoa.nao.encontrada"), pessoaId));
    }

    /**
     * Abaixo seguem metodos de alteração, eles utilizam o identificador long quando se busca pelo id ou uma classe
     * dto quando se busca por algum argumento.
     *
     * @return retorna o objeto alterado.
     */

    @Override
    public Pessoa alteraNomePessoa(long pessoaId, PessoaNomeChangeRequest pessoaNomeChangeRequest) {
        Pessoa pessoa = buscaPessoaPeloId(pessoaId);
        pessoa.setNome(pessoaNomeChangeRequest.getNome());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alteraCorpoEscolarPessoa(long pessoaId, PessoaCorpoChangeRequest pessoaCorpoChangeRequest) {
        Pessoa pessoa = buscaPessoaPeloId(pessoaId);
        pessoa.setCorpo(pessoaCorpoChangeRequest.getCorpo());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alteraSexoPessoa(long pessoaId, PessoaSexoChangeRequest pessoaSexoChangeRequest) {
        Pessoa pessoa = buscaPessoaPeloId(pessoaId);
        pessoa.setSexo(pessoaSexoChangeRequest.getSexo());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alteraDataNascimentoPessoa(long pessoaId, PessoaDataNascimentoChangeRequest pessoaDataNascimentoChangeRequest) {
        Pessoa pessoa = buscaPessoaPeloId(pessoaId);
        pessoa.setDataNascimento(pessoaDataNascimentoChangeRequest.getDataNascimento());
        return pessoaRepository.save(pessoa);
    }

    /**
     * Abaixo segue o metodo de criar, ele recebe a classe dto com os dados de criação e cria o objeto.
     *
     * @return retorna o objeto criado.
     */

    @Override
    public Pessoa criaPessoa(PessoaCreateRequest pessoaCreateRequest) {
        Pessoa pessoa = modelMapper.map(pessoaCreateRequest, Pessoa.class);
        return pessoaRepository.save(pessoa);
    }

    /**
     * Abaixo segue o metodo de deletar, ele recebe a classe dto com os dados de deleção e deleta o objeto.
     *
     * @return retorna o objeto deletado.
     */

    @Override
    public void deletaPessoa(long pessoaId) {
        try {
            pessoaRepository.deleteById(pessoaId);
        }catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(MessageUtils.buscarMensagem("pessoa.deletar.nao.encontrada", pessoaId));
        } catch (GenericException ex) {
            throw new GenericException(MessageUtils.buscarMensagem("response.code500"));
        }
    }
}
