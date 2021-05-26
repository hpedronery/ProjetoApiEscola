package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.cidade.CidadeFetchRequest;
import br.com.nova.api.escola.dtos.cidade.CidadeRetornoDto;
import br.com.nova.api.escola.model.Cidade;
import br.com.nova.api.escola.services.CidadeService;
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

@Api(value = "CidadeLeituraResource", tags = {"Cidades, Leitura"})
@RestController
@RequestMapping(value = "/cidades")
public class CidadeLeituraResource {

    /**
     * Classe responsável por realizar consulta de cidades de acordo com os parâmetros definidos.
     */

    private final CidadeService cidadeService;
    private final ModelMapper modelMapper;

    @Autowired
    public CidadeLeituraResource(CidadeService cidadeService, ModelMapper modelMapper) {
        this.cidadeService = cidadeService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(
            value = "Busca uma lista de cidades.",
            notes = "Recurso responsável por consultar uma lista de cidades de acordo com os parâmetros da requisição"
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Cidade(s) consultada(s) com sucesso.")}
    )
    @GetMapping
    public Response<List<CidadeRetornoDto>> buscaCidadePeloNomeEEstado(CidadeFetchRequest cidadeFetchRequest) {
        List<Cidade> cidades = cidadeService.buscaListaDeCidades(cidadeFetchRequest);
        return Response.ok(modelMapper.map(cidades, new TypeToken<List<CidadeRetornoDto>>() {
        }.getType()));
    }

    @ApiOperation(
            value = "Busca uma cidade pelo id.",
            notes = "Recurso responsável por consultar uma cidade de acordo com o parâmetro da requisição"
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Cidade(s) consultada(s) com sucesso.")}
    )
    @GetMapping("/{cidadeId}")
    public Response<CidadeRetornoDto> buscaCidadePeloId(@PathVariable long cidadeId) {
        Cidade cidade = cidadeService.buscaCidadePeloId(cidadeId);
        return Response.ok(modelMapper.map(cidade, CidadeRetornoDto.class));
    }
}
