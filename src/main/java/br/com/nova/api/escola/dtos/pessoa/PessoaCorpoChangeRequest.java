package br.com.nova.api.escola.dtos.pessoa;

import br.com.nova.api.escola.enums.CorpoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ApiModel(value = "DTO para alteração do corpo de uma pessoa.")
public class PessoaCorpoChangeRequest {

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Novo corpo para a pessoa")
    private CorpoEnum corpo;

    public CorpoEnum getCorpo() {
        return corpo;
    }

    public void setCorpo(CorpoEnum corpo) {
        this.corpo = corpo;
    }
}