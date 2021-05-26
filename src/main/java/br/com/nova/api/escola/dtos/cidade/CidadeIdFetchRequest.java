package br.com.nova.api.escola.dtos.cidade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Dto para filtrar a busca de cidades pelo id")
public class CidadeIdFetchRequest {

    @ApiModelProperty(notes = "Filtra uma cidade pelo id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
