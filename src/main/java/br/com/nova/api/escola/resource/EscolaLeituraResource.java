package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.escola.EscolaFetchRequest;
import br.com.nova.api.escola.dtos.escola.EscolaRetornoDto;
import br.com.nova.api.escola.model.Escola;
import br.com.nova.api.escola.services.EscolaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "EscolaLeituraResource", tags = {"Escola, Leitura"})
@RestController
@RequestMapping(value = "/escolas")
public class EscolaLeituraResource {

    /**
     * Classe responsável por realizar consulta de escolas de acordo com os parâmetros definidos.
     */

    private final EscolaService escolaService;
    private final ModelMapper modelMapper;

    @Autowired
    public EscolaLeituraResource(EscolaService escolaService, ModelMapper modelMapper) {
        this.escolaService = escolaService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(
            value = "Busca uma lista de escolas.",
            notes = "Recurso responsável por consultar uma lista de escolas de acordo com os parâmetros da requisição"
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Escolas(s) consultada(s) com sucesso.")}
    )
    @GetMapping
    public Response<List<EscolaRetornoDto>> buscarEscolas(EscolaFetchRequest escolaFetchRequest) {
        List<Escola> escolas = escolaService.buscaListaEscolas(escolaFetchRequest);
        return Response.ok(modelMapper.map(escolas, new TypeToken<List<EscolaRetornoDto>>() {
        }.getType()));
    }

    @ApiOperation(
            value = "Busca uma escola pelo id da cidade.",
            notes = "Recurso responsável por consultar uma lista de escolas de acordo com o parâmetro da requisição."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Escola(s) consultada(s) com sucesso.")}
    )
    @GetMapping("/cidadeId/{cidadeId}")
    public Response<List<EscolaRetornoDto>> buscaEscolaCidadeId(@PathVariable long cidadeId) {
        List<Escola> escolas = escolaService.buscarPelaCidadeId(cidadeId);
        return Response.ok(modelMapper.map(escolas, new TypeToken<List<EscolaRetornoDto>>() {
        }.getType()));
    }

    @ApiOperation(
            value = "Busca uma escola pelo id.",
            notes = "Recurso responsável por consultar uma escola de acordo com o parâmetro da requisição."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Escola consultada com sucesso.")}
    )
    @GetMapping("/escolaId/{escolaId}")
    public Response<EscolaRetornoDto> buscaEscolaPeloId(@PathVariable long escolaId) {
        Escola escola = escolaService.buscarPeloId(escolaId);
        return Response.ok(modelMapper.map(escola, EscolaRetornoDto.class));
    }
}
