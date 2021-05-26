package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Pessoa;
import br.com.nova.api.escola.repositories.EscolaRepository;
import br.com.nova.api.escola.repositories.PessoaRepository;
import br.com.nova.api.escola.repositories.specifications.PessoaSpecification;
import br.com.nova.api.escola.services.PessoaService;
import br.com.nova.api.escola.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Pessoa> buscarListaPessoas(PessoaFetchRequest pessoaFetchRequest) {
        PessoaSpecification pessoaSpecification = new PessoaSpecification(pessoaFetchRequest);
        return pessoaRepository.findAll(pessoaSpecification);
    }

    @Override
    public List<Pessoa> buscarPelaEscolaId(long escolaId) {
        List<Pessoa> pessoas = pessoaRepository.findByEscolaId(escolaId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("pessoa.escola.nao.encontrada"), escolaId));
        return pessoas;
    }

    @Override
    public Pessoa buscarPeloId(long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("pessoa.nao.encontrada"), pessoaId));
    }

    @Override
    public Pessoa salvarPessoa(PessoaCreateRequest pessoaCreateRequest) {
        Pessoa pessoa = modelMapper.map(pessoaCreateRequest, Pessoa.class);
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alterarNomePessoa(long pessoaId, PessoaNomeChangeRequest pessoaNomeChangeRequest) {
        Pessoa pessoa = buscarPeloId(pessoaId);
        pessoa.setNome(pessoaNomeChangeRequest.getNome());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alterarCorpoPessoa(long pessoaId, PessoaCorpoChangeRequest pessoaCorpoChangeRequest) {
        Pessoa pessoa = buscarPeloId(pessoaId);
        pessoa.setCorpo(pessoaCorpoChangeRequest.getCorpo());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alterarSexoPessoa(long pessoaId, PessoaSexoChangeRequest pessoaSexoChangeRequest) {
        Pessoa pessoa = buscarPeloId(pessoaId);
        pessoa.setSexo(pessoaSexoChangeRequest.getSexo());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public void deletarPessoa(long pessoaId) {
        Pessoa pessoa = buscarPeloId(pessoaId);
        pessoaRepository.delete(pessoa);
    }

    @Override
    public Pessoa alterarDataNascimento(long pessoaId, PessoaDataNascimentoChangeRequest pessoaDataNascimentoChangeRequest) {
        Pessoa pessoa = buscarPeloId(pessoaId);
        pessoa.setDataNascimento(pessoaDataNascimentoChangeRequest.getDataNascimento());
        return pessoaRepository.save(pessoa);
    }
}
