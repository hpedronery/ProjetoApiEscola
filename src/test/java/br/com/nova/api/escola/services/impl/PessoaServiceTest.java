package br.com.nova.api.escola.services.impl;

import br.com.nova.api.escola.configs.ModelMapperConfig;
import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.enums.CorpoEnum;
import br.com.nova.api.escola.enums.SexoEnum;
import br.com.nova.api.escola.exceptions.GenericException;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.model.Escola;
import br.com.nova.api.escola.model.Pessoa;
import br.com.nova.api.escola.repositories.EscolaRepository;
import br.com.nova.api.escola.repositories.PessoaRepository;
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
public class PessoaServiceTest {

    private static final String MOCK_FOLDER_ESCOLA = "mocks/escola";
    private static final String MOCK_OBJECT_ESCOLA = "escola.json";
    private static final String MOCK_FOLDER_PESSOA = "mocks/pessoa";
    private static final String MOCK_OBJECT_PESSOA = "pessoa.json";

    @MockBean
    private EscolaRepository escolaRepository;
    @MockBean
    private PessoaRepository pessoaRepository;
    private ModelMapper mapper = new ModelMapperConfig().modelMapper();

    private PessoaServiceImpl service() {
        return new PessoaServiceImpl(pessoaRepository, escolaRepository, mapper);
    }

    /**
     * Retorna um Mock de {@link Pessoa} completo e válido
     * @return objeto {@link Pessoa} completo e válido
     */
    protected static Pessoa getMockPessoa() {
        return TestUtils.getMock(MOCK_FOLDER_PESSOA, MOCK_OBJECT_PESSOA, Pessoa.class);
    }
    protected static Escola getMockEscola() {
        return TestUtils.getMock(MOCK_FOLDER_ESCOLA, MOCK_OBJECT_ESCOLA, Escola.class);
    }

    @Test
    public void consistenciaDosDados() {
        Pessoa pessoa = getMockPessoa();
        assertEquals(pessoa.getId(), 4);
        assertEquals(pessoa.getCorpo().getNome(), "Corpo Docente");
        assertEquals(pessoa.getNome(), "Fulano");
        assertEquals(pessoa.getSexo().getNome(), "Masculino");
        assertEquals(pessoa.getEscola().getId(), 3);
        assertEquals(pessoa.getEscola().getRedeEscola().getNome(), "Escola Municipal");
        assertEquals(pessoa.getEscola().getNome(), "Escola");
        assertEquals(pessoa.getEscola().getCidade().getNome(), "Ubá");
        assertEquals(pessoa.getEscola().getCidade().getEstado().getNome(), "Minas Gerais");
        assertEquals(pessoa.getEscola().getCidade().getId(), 2);
    }

    @Test
    public void testarBuscaPorId_RegistroExiste() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        Pessoa pessoaBuscada = service().buscarPeloId(pessoa.getId());
        assertEquals(pessoa.getId(), pessoaBuscada.getId());
    }

    @Test
    public void testarBuscaPorId_RegistroNaoExiste() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service().buscarPeloId(pessoa.getId()));
    }

    @Test
    public void testarBuscaPorEscolaId_RegistroExiste() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findByEscolaId(pessoa.getEscola().getId())).thenReturn(Optional.empty());
        System.out.println("SO PRA SABE");
        System.out.println(service().buscarPelaEscolaId(pessoa.getEscola().getId()).get(0).getId());
        List<Pessoa> pessoaBuscada = service().buscarPelaEscolaId(pessoa.getEscola().getId());
        assertEquals(pessoa.getId(), pessoaBuscada.get(0).getId());
    }

    @Test
    public void testarBuscaPorEscolaId_RegistroNaoExiste() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findByEscolaId(pessoa.getEscola().getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service().buscarPelaEscolaId(pessoa.getEscola().getId()).get(0));
    }

    @Test
    public void testarBuscaPessoasPeloNomeSexoCorpoEscolar() {
        PessoaFetchRequest pessoaFetchRequest = new PessoaFetchRequest();
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findAll(Mockito.any())).thenReturn(Collections.singletonList(pessoa));
        pessoaFetchRequest.setNome(pessoa.getNome());
        pessoaFetchRequest.setCorpo(pessoa.getCorpo());
        pessoaFetchRequest.setSexo(pessoa.getSexo());
        List<Pessoa> pessoas = service().buscarListaPessoas(pessoaFetchRequest);
        assertEquals(1, pessoas.size());
        assertEquals(pessoa.getId(), pessoas.get(0).getId());
        assertEquals(pessoa.getNome(), pessoas.get(0).getNome());
        assertEquals(pessoa.getCorpo().getNome(), pessoas.get(0).getCorpo().getNome());
        assertEquals(pessoa.getSexo().getNome(), pessoas.get(0).getSexo().getNome());
    }

    @Test
    public void testarBuscaPessoasPeloNomeSexoCorpoEscolar_NaoExistem() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.empty());
        assertThrows(IndexOutOfBoundsException.class, () -> service().buscarListaPessoas(new PessoaFetchRequest()).get(0));
    }

    @Test
    public void testarSalvarPessoa() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoa);
        Pessoa pessoaSalva = service().salvarPessoa(new PessoaCreateRequest());
        assertEquals(pessoa.getId(), pessoaSalva.getId());
    }

    @Test
    public void testaAlterarNomePessoa_PessoaExiste() {
        PessoaNomeChangeRequest pessoaNomeChangeRequest = new PessoaNomeChangeRequest();
        pessoaNomeChangeRequest.setNome("novo nome");
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoa);
        Pessoa pessoaSalva = service().alterarNomePessoa(pessoa.getId(), pessoaNomeChangeRequest);
        assertEquals(pessoaNomeChangeRequest.getNome(), pessoaSalva.getNome());
    }

    @Test
    public void testaAlterarNomePessoa_PessoaNaoExiste() {
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.empty());
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoa);
        assertThrows(NotFoundException.class, () -> service()
                .alterarNomePessoa(pessoa.getId(), new PessoaNomeChangeRequest()));
    }

    @Test
    public void testaAlterarSexoPessoa() {
        PessoaSexoChangeRequest pessoaSexoChangeRequest = new PessoaSexoChangeRequest();
        pessoaSexoChangeRequest.setSexo(SexoEnum.FEMININO);
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoa);
        Pessoa pessoaSalva = service().alterarSexoPessoa(pessoa.getId(), pessoaSexoChangeRequest);
        assertEquals(pessoaSexoChangeRequest.getSexo().getNome(), pessoaSalva.getSexo().getNome());
    }

    @Test
    public void testaAlterarCorpoEscolarPessoa() {
        PessoaCorpoChangeRequest pessoaCorpoChangeRequest = new PessoaCorpoChangeRequest();
        pessoaCorpoChangeRequest.setCorpo(CorpoEnum.CORPO_DISCENTE);
        Pessoa pessoa = getMockPessoa();
        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(Mockito.any())).thenReturn(pessoa);
        Pessoa pessoaSalva = service().alterarCorpoPessoa(pessoa.getId(), pessoaCorpoChangeRequest);
        assertEquals(pessoaCorpoChangeRequest.getCorpo().getNome(), pessoaSalva.getCorpo().getNome());
    }


    @Test
    public void testaDeletarPessoa_PessoaExiste() {
        Pessoa pessoa = getMockPessoa();
        service().deletarPessoa(pessoa.getId());
        verify(pessoaRepository, Mockito.times(1)).deleteById(pessoa.getId());
    }

    @Test
    public void testaDeletarCidade_CidadeNaoExiste() {
        Pessoa pessoa = getMockPessoa();
        doThrow(EmptyResultDataAccessException.class).when(pessoaRepository).deleteById(pessoa.getId());
        assertThrows(NotFoundException.class, () -> service().deletarPessoa(pessoa.getId()));
    }

    @Test
    public void testaDeletarCidade_ErroInterno() {
        Pessoa pessoa = getMockPessoa();
        doThrow(GenericException.class).when(pessoaRepository).deleteById(pessoa.getId());
        assertThrows(GenericException.class, () -> service().deletarPessoa(pessoa.getId()));
    }
}
