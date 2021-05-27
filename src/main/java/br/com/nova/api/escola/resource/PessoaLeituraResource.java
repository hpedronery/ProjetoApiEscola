package br.com.nova.api.escola.resource;

import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.dtos.pessoa.PessoaFetchRequest;
import br.com.nova.api.escola.dtos.pessoa.PessoaRetornoDto;
import br.com.nova.api.escola.model.Pessoa;
import br.com.nova.api.escola.services.PessoaService;
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

@Api(value = "PessoaLeituraResource", tags = {"Pessoa, Leitura"})
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaLeituraResource {

//  Classe responsável por realizar consulta de pessoas de acordo com os parâmetros definidos.

    private final PessoaService pessoaService;
    private final ModelMapper modelMapper;

    @Autowired
    public PessoaLeituraResource(PessoaService pessoaService, ModelMapper modelMapper) {
        this.pessoaService = pessoaService;
        this.modelMapper = modelMapper;
    }


    @ApiOperation(
            value = "Busca uma lista de pessoas.",
            notes = "Recurso responsável por consultar uma lista de pessoas de acordo com os parâmetros da requisição"
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Pessoa(s) consultada(s) com sucesso.")}
    )
    @GetMapping
    public Response<List<PessoaRetornoDto>> buscaPessoas(PessoaFetchRequest pessoaFetchRequest) {
        List<Pessoa> pessoas = pessoaService.buscaListaPessoas(pessoaFetchRequest);
        return Response.ok(modelMapper.map(pessoas, new TypeToken<List<PessoaRetornoDto>>() {
        }.getType()));
    }

    @ApiOperation(
            value = "Busca uma pessoa pelo id da escola.",
            notes = "Recurso responsável por consultar uma lista de pessoas de acordo com o parâmetro da requisição."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Escola(s) consultada(s) com sucesso.")}
    )
    @GetMapping("/escolaId/{escolaId}")
    public Response<List<PessoaRetornoDto>> buscaPessoaEscolaId(@PathVariable long escolaId) {
        List<Pessoa> pessoas = pessoaService.buscaPessoaPelaEscolaId(escolaId);
        return Response.ok(modelMapper.map(pessoas, new TypeToken<List<PessoaRetornoDto>>() {
        }.getType()));
    }

    @ApiOperation(
            value = "Busca uma pessoa pelo id.",
            notes = "Recurso responsável por consultar uma pessoa de acordo com o parâmetro da requisição."
    )
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Pessoa consultada com sucesso.")}
    )
    @GetMapping("/pessoaId/{pessoaId}")
    public Response<PessoaRetornoDto> buscaPessoaPeloId(@PathVariable long pessoaId) {
        Pessoa pessoa = pessoaService.buscaPessoaPeloId(pessoaId);
        return Response.ok(modelMapper.map(pessoa, PessoaRetornoDto.class));
    }
}
