package br.com.nova.api.escola.dtos.escola;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DTO para alteração da cidade da escola.")
public class EscolaCidadeChangeRequest {

    @ApiModelProperty(notes = "Escolhe uma cidade nova para a escola pelo ID")
    private long novaCidadeId;

    public long getNovaCidadeId() {
        return novaCidadeId;
    }

    public void setNovaCidadeId(long novaCidadeId) {
        this.novaCidadeId = novaCidadeId;
    }
}