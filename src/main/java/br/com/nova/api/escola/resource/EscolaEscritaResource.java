package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.escola.*;
import br.com.nova.api.escola.model.Escola;
import br.com.nova.api.escola.services.EscolaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "EscolaEscritaResource", tags = {"Escola, Escrita"})
@RestController
@RequestMapping(value = "/escolas")
public class EscolaEscritaResource {

    private final EscolaService escolaService;
    private final ModelMapper modelMapper;

    @Autowired
    public EscolaEscritaResource(EscolaService escolaService, ModelMapper modelMapper) {
        this.escolaService = escolaService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(
            value = "Insere/Cria uma nova Escola.",
            notes = "Recurso responsável por Inserir/Criar uma nova Escola."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Escola inserida/criada com sucesso."),
                    @ApiResponse(code = 400, message = "Erros de validação na requisição")})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Response<EscolaRetornoDto> criarEscola(@RequestBody EscolaCreateRequest escolaCreateRequest) {
        Escola escola = escolaService.salvarEscola(escolaCreateRequest);
        return Response.created(modelMapper.map(escola, EscolaRetornoDto.class));
    }

    @ApiOperation(
            value = "Altera o nome de uma Escola.",
            notes = "Recurso responsável por alterar o nome de uma Escola."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Nome da escola alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarNome/{escolaId}")
    public Response<String> alterarNomeEscola(@PathVariable long escolaId,
                                              @RequestBody EscolaNomeChangeRequest escolaNomeChangeRequest) {
        Escola escola = escolaService.alterarNomeEscola(escolaId, escolaNomeChangeRequest);
        return Response.ok(escola.getNome());
    }

    @ApiOperation(
            value = "Altera a rede de uma Escola.",
            notes = "Recurso responsável por alterar a rede de uma Escola."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Rede da escola alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarRede/{escolaId}")
    public Response<String> alterarRedeEscola(@PathVariable long escolaId,
                                              @RequestBody EscolaRedeChangeRequest escolaRedeChangeRequest) {
        Escola escola = escolaService.alterarRedeEscola(escolaId, escolaRedeChangeRequest);
        return Response.ok(escola.getRedeEscola().getNome());
    }

    @ApiOperation(
            value = "Altera a cidade de uma Escola.",
            notes = "Recurso responsável por alterar a cidade de uma Escola."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Cidade da escola alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarCidade/{escolaId}")
    public Response<String> alterarCidadeEscola(@PathVariable long escolaId,
                                                @RequestBody EscolaCidadeChangeRequest escolaCidadeChangeRequest) {
        Escola escola = escolaService.alterarCidadeEscola(escolaId, escolaCidadeChangeRequest.getNovaCidadeId());
        return Response.ok(escola.getCidade().getNome());
    }

    @ApiOperation(
            value = "Deletar a escola por seu identificador.",
            notes = "Recurso responsável por deletar uma escola."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 204, message = "Escola deletada com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{escolaId}")
    public Response<Void> deletarEscola(@PathVariable long escolaId) {
        escolaService.deletarEscola(escolaId);
        return Response.deleted();
    }
}
