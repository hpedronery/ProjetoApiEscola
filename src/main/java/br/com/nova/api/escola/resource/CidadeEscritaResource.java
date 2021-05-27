package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.cidade.CidadeCreateRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeEstadoChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeNomeChangeRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeRetornoDto;
import br.com.nova.api.escola.model.Cidade;
import br.com.nova.api.escola.services.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "CidadeEscritaResource", tags = {"Cidade, Escrita"})
@RestController
@RequestMapping(value = "/cidades")
public class CidadeEscritaResource {


//  Classe responsável por alterar/criar/deletar dados de uma cidade.


    private final CidadeService cidadeService;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeEscritaResource(CidadeService cidadeService, ModelMapper modelMapper) {
        this.cidadeService = cidadeService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(
            value = "Altera o nome de uma Cidade.",
            notes = "Recurso responsável por alterar o nome de uma Cidade."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Nome da cidade alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarNome/{cidadeId}")
    public Response<String> alteraNomeCidade(@PathVariable long cidadeId,
                                             @RequestBody CidadeNomeChangeRequest cidadeNomeChangeRequest) {
        Cidade cidade = cidadeService.alteraNomeCidade(cidadeId, cidadeNomeChangeRequest);
        return Response.ok(cidade.getNome());
    }

    @ApiOperation(
            value = "Altera o estado de uma Cidade.",
            notes = "Recurso responsável por alterar o estado de uma Cidade."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Estado da cidade alterado com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @PatchMapping("/alterarEstado/{cidadeId}")
    public Response<String> alteraEstadoCidade(@PathVariable long cidadeId,
                                               @RequestBody CidadeEstadoChangeRequest cidadeEstadoChangeRequest) {
        Cidade cidade = cidadeService.alteraEstadoCidade(cidadeId, cidadeEstadoChangeRequest);
        return Response.ok(cidade.getNome());
    }

    @ApiOperation(
            value = "Insere/Cria uma nova Cidade.",
            notes = "Recurso responsável por Inserir/Criar uma nova Cidade."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Cidade inserida/criada com sucesso."),
                    @ApiResponse(code = 400, message = "Erros de validação na requisição")})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Response<CidadeRetornoDto> criaCidade(@RequestBody CidadeCreateRequest cidadeCreateRequest) {
        Cidade cidade = cidadeService.criaCidade(cidadeCreateRequest);
        return Response.created(modelMapper.map(cidade, CidadeRetornoDto.class));
    }

    @ApiOperation(
            value = "Deletar a cidade por seu identificador.",
            notes = "Recurso responsável por deletar uma cidade."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 204, message = "Cidade deletada com sucesso."),
                    @ApiResponse(code = 404, message = "Recursos necessários inválidos ou não encontrados.")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cidadeId}")
    public Response<Void> deletaCidade(@PathVariable long cidadeId) {
        cidadeService.deletaCidade(cidadeId);
        return Response.deleted();
    }
}
