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

    /**
     * Abaixo seguem metodos de busca, eles utilizam o identificador long quando se busca pelo id ou uma classe dto
     * quando se busca por algum argumento.
     *
     * @return retorna o(s) objeto(s) da busca.
     */

    @Override
    public Cidade buscaCidadePeloId(long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new NotFoundException(MessageUtils
                        .buscarMensagem("cidade.nao.encontrada"), cidadeId));
    }

    @Override
    public List<Cidade> buscaListaDeCidades(CidadeFetchRequest cidadeFetchRequest) {
        CidadeSpecification cidadeSpecification = new CidadeSpecification(cidadeFetchRequest);
        return cidadeRepository.findAll(cidadeSpecification);
    }

    /**
     * Abaixo seguem metodos de alteração, eles utilizam o identificador long quando se busca pelo id ou uma classe
     * dto quando se busca por algum argumento.
     *
     * @return retorna o objeto alterado.
     */

    @Override
    public Cidade alteraNomeCidade(long cidadeId, CidadeNomeChangeRequest cidadeNomeChangeRequest) {
        Cidade cidade = buscaCidadePeloId(cidadeId);
        cidade.setNome(cidadeNomeChangeRequest.getNome());
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade alteraEstadoCidade(long cidadeId, CidadeEstadoChangeRequest cidadeEstadoChangeRequest) {
        Cidade cidade = buscaCidadePeloId(cidadeId);
        cidade.setEstado(cidadeEstadoChangeRequest.getEstado());
        return cidadeRepository.save(cidade);
    }

    /**
     * Abaixo segue o metodo de criar, ele recebe a classe dto com os dados de criação e cria o objeto.
     *
     * @return retorna o objeto criado.
     */

    @Override
    public Cidade criaCidade(@Valid CidadeCreateRequest cidadeCreateRequest) {
        Cidade cidade = modelMapper.map(cidadeCreateRequest, Cidade.class);
        return cidadeRepository.save(cidade);
    }

    /**
     * Abaixo segue o metodo de deletar, ele recebe a classe dto com os dados de deleção e deleta o objeto.
     *
     * @return retorna o objeto deletado.
     */

    @Override
    public void deletaCidade(long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(MessageUtils.buscarMensagem("cidade.deletar.nao.encontrado", cidadeId));
        } catch (Exception ex) {
            throw new GenericException(MessageUtils.buscarMensagem("response.code500"));
        }
    }
}
