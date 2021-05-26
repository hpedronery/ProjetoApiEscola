package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.dtos.escola.EscolaCreateRequest;
import br.com.nova.api.escola.dtos.escola.EscolaFetchRequest;
import br.com.nova.api.escola.dtos.escola.EscolaNomeChangeRequest;
import br.com.nova.api.escola.dtos.escola.EscolaRedeChangeRequest;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Escola;
import br.com.nova.api.escola.repositories.CidadeRepository;
import br.com.nova.api.escola.repositories.EscolaRepository;
import br.com.nova.api.escola.repositories.specifications.EscolaSpecification;
import br.com.nova.api.escola.services.EscolaService;
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
public class EscolaServiceImpl implements EscolaService {

    private final CidadeRepository cidadeRepository;
    private final EscolaRepository escolaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EscolaServiceImpl(CidadeRepository cidadeRepository, EscolaRepository escolaRepository, ModelMapper modelMapper) {
        this.cidadeRepository = cidadeRepository;
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
    public Escola buscaEscolaPeloId(long escolaId) {
        return escolaRepository.findById(escolaId)
                .orElseThrow(() -> new NotFoundException(MessageUtils.buscarMensagem("escola.nao.encontrada"), escolaId));
    }

    @Override
    public List<Escola> buscaEscolaPelaCidadeId(long cidadeId) {
        try {
            List<Escola> escolas = escolaRepository.findByCidadeId(cidadeId);
            return escolas;
        } catch (NotFoundException ex) {
            MessageUtils.buscarMensagem("escola.cidade.nao.encontrada");
        }
        return null;
    }

    @Override
    public List<Escola> buscaListaEscolasPeloNomeOuRedeEscolar(EscolaFetchRequest escolaFetchRequest) {
        EscolaSpecification escolaSpecification = new EscolaSpecification(escolaFetchRequest);
        return escolaRepository.findAll(escolaSpecification);
    }

    /**
     * Abaixo seguem metodos de alteração, eles utilizam o identificador long quando se busca pelo id ou uma classe
     * dto quando se busca por algum argumento.
     *
     * @return retorna o objeto alterado.
     */

    @Override
    public Escola alteraNomeEscola(long escolaId, EscolaNomeChangeRequest escolaNomeChangeRequest) {
        Escola escola = buscaEscolaPeloId(escolaId);
        escola.setNome(escolaNomeChangeRequest.getNome());
        return escolaRepository.save(escola);
    }

    @Override
    public Escola alteraRedeEscola(long escolaId, EscolaRedeChangeRequest escolaRedeChangeRequest) {
        Escola escola = buscaEscolaPeloId(escolaId);
        escola.setRedeEscola(escolaRedeChangeRequest.getRedeEscola());
        return escolaRepository.save(escola);
    }

    @Override
    public Escola alteraCidadeEscola(long escolaId, long cidadeId) {
        Escola escola = buscaEscolaPeloId(escolaId);
        escola.setCidade(cidadeRepository
                .findById(cidadeId)
                .orElseThrow(() -> new NotFoundException(MessageUtils
                        .buscarMensagem("cidade.nao.encontrada")
                        , cidadeId)));
        return escolaRepository.save(escola);
    }

    /**
     * Abaixo segue o metodo de criar, ele recebe a classe dto com os dados de criação e cria o objeto.
     *
     * @return retorna o objeto criado.
     */

    @Override
    public Escola criaEscola(@Valid EscolaCreateRequest escolaCreateRequest) {
        Escola escola = modelMapper.map(escolaCreateRequest, Escola.class);
        return escolaRepository.save(escola);
    }

    /**
     * Abaixo segue o metodo de deletar, ele recebe a classe dto com os dados de deleção e deleta o objeto.
     *
     * @return retorna o objeto deletado.
     */

    @Override
    public void deletaEscola(long escolaId) {
        try {
            escolaRepository.deleteById(escolaId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(MessageUtils.buscarMensagem("escola.deletar.nao.encontrada", escolaId));
        } catch (GenericException ex) {
            throw new GenericException(MessageUtils.buscarMensagem("response.code500"));
        }
    }
}
