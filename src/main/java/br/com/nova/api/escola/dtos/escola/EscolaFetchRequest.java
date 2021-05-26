package br.com.nova.api.escola.dtos.escola;

import br.com.nova.api.escola.enums.RedeEscolaEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;

@ApiModel(value = "Dto para filtrar a busca de escolas")
public class EscolaFetchRequest {

    @ApiModelProperty(notes = "Filtra a lista por nome")
    private String nome;

    @ApiModelProperty(notes = "Filtra a lista pela rede de ensino")
    private RedeEscolaEnum redeEscola;

    @Nullable
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Nullable
    public RedeEscolaEnum getRedeEscola() {
        return redeEscola;
    }

    public void setRedeEscola(RedeEscolaEnum redeEscola) {
        this.redeEscola = redeEscola;
    }
}
