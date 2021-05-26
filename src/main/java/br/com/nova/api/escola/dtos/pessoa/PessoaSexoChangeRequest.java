package br.com.nova.api.escola.dtos.pessoa;

import br.com.nova.api.escola.enums.SexoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ApiModel(value = "DTO para alteração do sexo de uma pessoa.")
public class PessoaSexoChangeRequest {

    @ApiModelProperty(notes = "Novo sexo")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }
}
