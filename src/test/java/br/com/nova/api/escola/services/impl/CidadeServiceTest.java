package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.configs.ModelMapperConfig;
import br.com.nova.api.escola.dtos.cidade.CidadeCreateRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeEstadoChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeNomeChangeRequest;
import br.com.nova.api.escola.enums.EstadoEnum;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.repositories.CidadeRepository;
import br.com.nova.api.escola.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import br.com.nova.api.escola.model.Cidade;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class CidadeServiceTest {

    private static final String MOCK_FOLDER = "mocks/cidade";
    private static final String MOCK_OBJECT = "cidade.json";

    @MockBean
    private CidadeRepository cidadeRepository;
    private ModelMapper mapper = new ModelMapperConfig().modelMapper();

    private CidadeServiceImpl service() {
        return new CidadeServiceImpl(cidadeRepository, mapper);
    }

    /**
     * Retorna um Mock de {@link Cidade} completo e válido
     * @return objeto {@link Cidade} completo e válido
     */
    protected static Cidade getMock() {
        return TestUtils.getMock(MOCK_FOLDER, MOCK_OBJECT, Cidade.class);
    }

    /**
     * Abaixo seguem os testes para os metodos de busca.
     */

    @Test
    public void testaBuscaPorId_RegistroExiste() {
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.of(cidade));
        Cidade cidadeBuscada = service().buscaCidadePeloId(cidade.getId());
        assertEquals(cidade.getId(), cidadeBuscada.getId());
    }

    @Test
    public void testaBuscaPorId_RegistroNaoExiste() {
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service().buscaCidadePeloId(cidade.getId()));
    }

    @Test
    public void testarBuscaCidadesPeloNomeEPeloEstado() {
        CidadeFetchRequest cidadeFetchRequest = new CidadeFetchRequest();
        Cidade cidade = getMock();
        when(cidadeRepository.findAll(Mockito.any())).thenReturn(Collections.singletonList(cidade));
        cidadeFetchRequest.setNome(cidade.getNome());
        cidadeFetchRequest.setEstado(cidade.getEstado());
        List<Cidade> cidades = service().buscaListaDeCidades(cidadeFetchRequest);
        assertEquals(1, cidades.size());
        assertEquals(cidade.getId(), cidades.get(0).getId());
        assertEquals(cidade.getEstado().getNome(), cidades.get(0).getEstado().getNome());
    }

    @Test
    public void testarBuscaCidadesPeloNomeEPeloEstado_RegistroNaoExiste() {
        when(cidadeRepository.findAll(Mockito.any())).thenReturn(null);
        List<Cidade> cidades = service().buscaListaDeCidades(new CidadeFetchRequest());
        assertThrows(NullPointerException.class, () -> cidades.get(0));
    }

    /**
     * Abaixo seguem os testes para os métodos de alteração.
     */

    @Test
    public void testaAlterarNomeCidade_CidadeExiste() {
        CidadeNomeChangeRequest cidadeNomeChangeRequest = new CidadeNomeChangeRequest();
        cidadeNomeChangeRequest.setNome("novo nome");
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        Cidade cidadeSalva = service().alteraNomeCidade(cidade.getId(), cidadeNomeChangeRequest);
        assertEquals(cidadeNomeChangeRequest.getNome(), cidadeSalva.getNome());
    }

    @Test
    public void testaAlterarNomeCidade_CidadeNaoExiste() {
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        assertThrows(NotFoundException.class, () -> service().alteraNomeCidade(cidade.getId(), new CidadeNomeChangeRequest()));
    }

    @Test
    public void testaAlterarEstadoCidade_CidadeExiste() {
        CidadeEstadoChangeRequest cidadeEstadoChangeRequest = new CidadeEstadoChangeRequest();
        cidadeEstadoChangeRequest.setEstado(EstadoEnum.AC);
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        Cidade cidadeSalva = service().alteraEstadoCidade(cidade.getId(), cidadeEstadoChangeRequest);
        assertEquals(cidadeEstadoChangeRequest.getEstado(), cidadeSalva.getEstado());
    }

    @Test
    public void testaAlterarEstadoCidade_EstadoNaoEInformado() {
        Cidade cidade = getMock();
        when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        assertThrows(NotFoundException.class, () -> service().alteraEstadoCidade(cidade.getId(), new CidadeEstadoChangeRequest()));
    }

    /**
     * Abaixo segue o teste para o metodo de criar.
     */

    @Test
    public void testaCriaCidade() {
        Cidade cidade = getMock();
        when(cidadeRepository.save(Mockito.any())).thenReturn(cidade);
        Cidade cidadeSalva = service().criaCidade(new CidadeCreateRequest());
        assertEquals(cidade.getId(), cidadeSalva.getId());
    }

    /**
     * Abaixo seguem os testes para o metodo de deletar.
     */

    @Test
    public void testaDeletarCidade_CidadeExiste() {
        Cidade cidade = getMock();
        service().deletaCidade(cidade.getId());
        verify(cidadeRepository, Mockito.times(1)).deleteById(cidade.getId());
    }

    @Test
    public void testaDeletarCidade_CidadeNaoExiste() {
        Cidade cidade = getMock();
        doThrow(EmptyResultDataAccessException.class).when(cidadeRepository).deleteById(cidade.getId());
        assertThrows(NotFoundException.class, () -> service().deletaCidade(cidade.getId()));
    }

    @Test
    public void testaDeletarCidade_ErroInterno() {
        Cidade cidade = getMock();
        doThrow(GenericException.class).when(cidadeRepository).deleteById(cidade.getId());
        assertThrows(GenericException.class, () -> service().deletaCidade(cidade.getId()));
    }
}
