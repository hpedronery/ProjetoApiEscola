package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.dtos.cidade.CidadeCreateRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeEstadoChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeNomeChangeRequest;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Cidade;
import br.com.nova.api.escola.repositories.CidadeRepository;
import br.com.nova.api.escola.repositories.specifications.CidadeSpecification;
import br.com.nova.api.escola.services.CidadeService;
import br.com.nova.api.escola.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeServiceImpl(CidadeRepository cidadeRepository, ModelMapper modelMapper) {
        this.cidadeRepository = cidadeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cidade buscarPeloId(long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("cidade.nao.encontrada"), cidadeId));
    }

    @Override
    public List<Cidade> buscaListaDeCidades(CidadeFetchRequest cidadeFetchRequest) {
        CidadeSpecification cidadeSpecification = new CidadeSpecification(cidadeFetchRequest);
        return cidadeRepository.findAll(cidadeSpecification);
    }

    @Override
    public Cidade salvarCidade(@Valid CidadeCreateRequest cidadeCreateRequest) {
        Cidade cidade = modelMapper.map(cidadeCreateRequest, Cidade.class);
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade alterarNomeCidade(long cidadeId, CidadeNomeChangeRequest cidadeNomeChangeRequest) {
        Cidade cidade = buscarPeloId(cidadeId);
        cidade.setNome(cidadeNomeChangeRequest.getNome());
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade alterarEstadoCidade(long cidadeId, CidadeEstadoChangeRequest cidadeEstadoChangeRequest) {
        Cidade cidade = buscarPeloId(cidadeId);
        cidade.setEstado(cidadeEstadoChangeRequest.getEstado());
        return cidadeRepository.save(cidade);
    }

    @Override
    public void deletarCidade(long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(MessageUtils.buscarMensagem("cidade.deletar.nao.encontrado", cidadeId));
        } catch (Exception ex) {
            throw new GenericException(MessageUtils.buscarMensagem("response.code500"));
        }
    }
}
