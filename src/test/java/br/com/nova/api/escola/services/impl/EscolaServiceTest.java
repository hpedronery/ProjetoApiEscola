package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.configs.ModelMapperConfig;
import br.com.nova.api.escola.dtos.escola.*;
import br.com.nova.api.escola.enums.RedeEscolaEnum;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Cidade;
import br.com.nova.api.escola.model.Escola;
import br.com.nova.api.escola.repositories.CidadeRepository;
import br.com.nova.api.escola.repositories.EscolaRepository;
import br.com.nova.api.escola.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EscolaServiceTest {

    private static final String MOCK_FOLDER_ESCOLA = "mocks/escola";
    private static final String MOCK_OBJECT_ESCOLA = "escola.json";
    private static final String MOCK_FOLDER_CIDADE = "mocks/cidade";
    private static final String MOCK_OBJECT_CIDADE = "cidade.json";

    @MockBean
    private CidadeRepository cidadeRepository;
    @MockBean
    private EscolaRepository escolaRepository;
    private ModelMapper mapper = new ModelMapperConfig().modelMapper();

    private EscolaServiceImpl service() {
        return new EscolaServiceImpl(cidadeRepository, escolaRepository, mapper);
    }

    /**
     * Retorna um Mock de {@link Escola} completo e válido
     * @return objeto {@link Escola} completo e válido
     */
    protected static Escola getMockEscola() {
        return TestUtils.getMock(MOCK_FOLDER_ESCOLA, MOCK_OBJECT_ESCOLA, Escola.class);
    }
    /**
     * Retorna um Mock de {@link Cidade} completo e válido
     * @return objeto {@link Cidade} completo e válido
     */
    protected static Cidade getMockCidade() {
        return TestUtils.getMock(MOCK_FOLDER_CIDADE, MOCK_OBJECT_CIDADE, Cidade.class);
    }

//       Abaixo seguem os testes para os metodos de busca.

    @Test
    public void testaBuscaEscolaPorId_RegistroExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.of(escola));
        Escola escolaBuscada = service().buscaEscolaPeloId(escola.getId());
        assertEquals(escola.getId(), escolaBuscada.getId());
    }

    @Test
    public void testarBuscaEscolaPorId_RegistroNaoExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service().buscaEscolaPeloId(escola.getId()));
    }

    @Test
    public void testarBuscaEscolasPeloNomeEPelaRedeDeEnsino() {
        EscolaFetchRequest escolaFetchRequest = new EscolaFetchRequest();
        Escola escola = getMockEscola();
        when(escolaRepository.findAll(Mockito.any())).thenReturn(Collections.singletonList(escola));
        escolaFetchRequest.setNome(escola.getNome());
        escolaFetchRequest.setRedeEscola(escola.getRedeEscola());
        List<Escola> escolas = service().buscaListaEscolasPeloNomeOuRedeEscolar(escolaFetchRequest);
        assertEquals(1, escolas.size());
        assertEquals(escola.getId(), escolas.get(0).getId());
        assertEquals(escola.getRedeEscola(), escolas.get(0).getRedeEscola());
    }

    @Test
    public void testarBuscaEscolaPorCidadeId_RegistroExiste() {
        Escola escola = getMockEscola();
        List<Escola> escolas = new ArrayList<>();
        when(escolaRepository.findByCidadeId(escola.getCidade().getId())).thenReturn(Collections.singletonList(escola));
        escolas.add(service().buscaEscolaPelaCidadeId(escola.getCidade().getId()).get(0));
        assertEquals(escola.getCidade().getId(), escolas.get(0).getCidade().getId());
    }

    @Test
    public void testarBuscaEscolaPorCidadeId_RegistroNaoExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findByCidadeId(escola.getCidade().getId())).thenReturn(Collections.singletonList(escola));
        assertThrows(NotFoundException.class, () -> service().buscaEscolaPeloId(escola.getId()));
    }

//      Abaixo seguem os testes para os métodos de alteração.

    @Test
    public void testaAlterarNomeEscola_EscolaExiste() {
        EscolaNomeChangeRequest escolaNomeChangeRequest = new EscolaNomeChangeRequest();
        escolaNomeChangeRequest.setNome("novo nome");
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.of(escola));
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        Escola escolaSalva = service().alteraNomeEscola(escola.getId(), escolaNomeChangeRequest);
        assertEquals(escolaNomeChangeRequest.getNome(), escolaSalva.getNome());
    }

    @Test
    public void testaAlterarNomeEscola_EscolaNaoExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.empty());
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        assertThrows(NotFoundException.class, () -> service()
                .alteraNomeEscola(escola.getId(), new EscolaNomeChangeRequest()));
    }

    @Test
    public void testaAlterarCidadeEscola_EscolaExiste() {
        Escola escola = getMockEscola();
        Cidade cidadeNova = getMockCidade();
        when(cidadeRepository.findById(cidadeNova.getId())).thenReturn(Optional.of(cidadeNova));
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.of(escola));
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        Escola escolaSalva = service().alteraCidadeEscola(escola.getId(), cidadeNova.getId());
        assertEquals(cidadeNova.getId(), escolaSalva.getCidade().getId());
    }

    @Test
    public void testaAlterarCidadeEscola_CidadeNaoExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.of(escola));
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        assertThrows(NotFoundException.class, () -> service()
                .alteraCidadeEscola(escola.getId(), new Cidade().getId()));
    }

    @Test
    public void testaAlterarRedeEscolar_CidadeExiste() {
        EscolaRedeChangeRequest escolaRedeChangeRequest = new EscolaRedeChangeRequest();
        escolaRedeChangeRequest.setRedeEscola(RedeEscolaEnum.EM);
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.of(escola));
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        Escola escolaSalva = service().alteraRedeEscola(escola.getId(), escolaRedeChangeRequest);
        assertEquals(escolaRedeChangeRequest.getRedeEscola(), escolaSalva.getRedeEscola());
    }

    @Test
    public void testaAlterarRedeEscolar_CidadeNaoExiste() {
        Escola escola = getMockEscola();
        when(escolaRepository.findById(escola.getId())).thenReturn(Optional.empty());
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        assertThrows(NotFoundException.class, () -> service().alteraRedeEscola(escola.getId(),
                new EscolaRedeChangeRequest()));
    }

//      Abaixo segue o teste para o metodo de criar.

    @Test
    public void testarSalvarEscola() {
        Escola escola = getMockEscola();
        when(escolaRepository.save(Mockito.any())).thenReturn(escola);
        Escola escolaSalva = service().criaEscola(new EscolaCreateRequest());
        assertEquals(escola.getId(), escolaSalva.getId());
    }

//     Abaixo seguem os testes para o metodo de deletar.

    @Test
    public void testaDeletarEscola_EscolaExiste() {
        Escola escola = getMockEscola();
        service().deletaEscola(escola.getId());
        verify(escolaRepository, Mockito.times(1)).deleteById(escola.getId());
    }

    @Test
    public void testaDeletaEscola_EscolaNaoExiste() {
        Escola escola = getMockEscola();
        doThrow(EmptyResultDataAccessException.class).when(escolaRepository).deleteById(escola.getId());
        assertThrows(NotFoundException.class, () -> service().deletaEscola(escola.getId()));
    }

    @Test
    public void testaDeletarEscola_ErroInterno() {
        Escola escola = getMockEscola();
        doThrow(GenericException.class).when(escolaRepository).deleteById(escola.getId());
        assertThrows(GenericException.class, () -> service().deletaEscola(escola.getId()));
    }
}
