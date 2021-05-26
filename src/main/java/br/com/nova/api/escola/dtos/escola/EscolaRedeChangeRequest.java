package br.com.nova.api.escola.dtos.escola;

import br.com.nova.api.escola.enums.RedeEscolaEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ApiModel(value = "DTO para alteração da rede da escola.")
public class EscolaRedeChangeRequest {

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Nova rede para a escola")
    private RedeEscolaEnum redeEscola;

    public RedeEscolaEnum getRedeEscola() {
        return redeEscola;
    }

    public void setRedeEscola(RedeEscolaEnum redeEscola) {
        this.redeEscola = redeEscola;
    }
}