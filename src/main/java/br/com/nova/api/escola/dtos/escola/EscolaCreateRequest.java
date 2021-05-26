package br.com.nova.api.escola.dtos.escola;

import br.com.nova.api.escola.enums.RedeEscolaEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "Dto para criar uma escola")
public class EscolaCreateRequest {

    @NotBlank(message = "{escola.nome.vazio}")
    @Size(max = 255, message = "{escola.nome.tamanho}")
    @ApiModelProperty(notes = "O nome da escola.")
    private String nome;

    @NotNull(message = "{escola.rede.vazia}")
    @ApiModelProperty(notes = "A rede da escola.")
    private RedeEscolaEnum redeEscola;

    @NotNull(message = "{escola.cidade.id.vazio}")
    private long cidadeId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RedeEscolaEnum getRedeEscola() {
        return redeEscola;
    }

    public void setRedeEscola(RedeEscolaEnum redeEscola) {
        this.redeEscola = redeEscola;
    }

    public long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(long cidadeId) {
        this.cidadeId = cidadeId;
    }
}
