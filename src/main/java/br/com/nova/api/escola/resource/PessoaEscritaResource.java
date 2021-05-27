package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.pessoa.*;
import br.com.nova.api.escola.model.Pessoa;
import br.com.nova.api.escola.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Api(value = "PessoaEscritaResource", tags = {"Pessoa, Escrita"})
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaEscritaResource {

//  Classe responsável por alterar/criar/deletar dados de uma pessoa

    private final PessoaService pessoaService;
    private final ModelMapper modelMapper;

    @Autowired
    public PessoaEscritaResource(PessoaService pessoaService, ModelMapper modelMapper) {
        this.pessoaService = pessoaService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(
            value = "Altera o nome de uma Pessoa.",
            notes = "Recurso responsável por alterar o nome de uma Pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Nome da pessoa alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarNome/{pessoaId}")
    public Response<String> alteraNomePessoa(@PathVariable long pessoaId,
                                        @RequestBody PessoaNomeChangeRequest pessoaNomeChangeRequest) {
        Pessoa pessoa = pessoaService.alteraNomePessoa(pessoaId, pessoaNomeChangeRequest);
        return Response.ok(pessoa.getNome());
    }

    @ApiOperation(
            value = "Altera o corpo de uma Pessoa.",
            notes = "Recurso responsável por alterar o corpo de uma Pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Corpo da pessoa alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarCorpo/{pessoaId}")
    public Response<String> alteraCorpoEscolarPessoa(@PathVariable long pessoaId,
                                         @RequestBody PessoaCorpoChangeRequest pessoaCorpoChangeRequest) {
        Pessoa pessoa = pessoaService.alteraCorpoEscolarPessoa(pessoaId, pessoaCorpoChangeRequest);
        return Response.ok(pessoa.getCorpo().getNome());
    }

    @ApiOperation(
            value = "Altera o sexo de uma Pessoa.",
            notes = "Recurso responsável por alterar o sexo de uma Pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Sexo da pessoa alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarSexo/{pessoaId}")
    public Response<String> alteraSexoPessoa(@PathVariable long pessoaId,
                                        @RequestBody PessoaSexoChangeRequest pessoaSexoChangeRequest) {
        Pessoa pessoa = pessoaService.alteraSexoPessoa(pessoaId, pessoaSexoChangeRequest);
        return Response.ok(pessoa.getSexo().getNome());
    }

    @ApiOperation(
            value = "Altera a data de nascimento de uma pessoa.",
            notes = "Recurso responsável por alterar a data de nascimento de uma Pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Data de nascimento alterada com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarDataNascimento/{pessoaId}")
    public Response<LocalDate> alteraDataNascimentoPessoa(@PathVariable long pessoaId,
                                                     @RequestBody PessoaDataNascimentoChangeRequest pessoaDataNascimentoChangeRequest) {
        Pessoa pessoa = pessoaService.alteraDataNascimentoPessoa(pessoaId, pessoaDataNascimentoChangeRequest);
        return Response.ok(pessoa.getDataNascimento());
    }

    @ApiOperation(
            value = "Insere/Cria uma nova Pessoa.",
            notes = "Recurso responsável por Inserir/Criar uma nova Pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Pessoa inserida/criada com sucesso."),
                    @ApiResponse(code = 400, message = "Erros de validação na requisição")})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Response<PessoaRetornoDto> criaPessoa(@RequestBody PessoaCreateRequest pessoaCreateRequest) {
        Pessoa pessoa = pessoaService.criaPessoa(pessoaCreateRequest);
        return Response.created(modelMapper.map(pessoa, PessoaRetornoDto.class));
    }

    @ApiOperation(
            value = "Deletar a pessoa por seu identificador.",
            notes = "Recurso responsável por deletar uma pessoa."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 204, message = "pessoa deletada com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{pessoaId}")
    public Response<Void> deletaPessoa(@PathVariable long pessoaId) {
        pessoaService.deletaPessoa(pessoaId);
        return Response.deleted();
    }
}
