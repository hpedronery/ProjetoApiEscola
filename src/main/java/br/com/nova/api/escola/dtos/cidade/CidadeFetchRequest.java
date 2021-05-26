package br.com.nova.api.escola.dtos.cidade;

import br.com.nova.api.escola.enums.EstadoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;

@ApiModel(value = "Dto para filtrar a busca de cidades")
public class CidadeFetchRequest {

    @ApiModelProperty(notes = "Filtra a lista por nome")
    private String nome;

    @ApiModelProperty(notes = "Filtra a lista pelo estado")
    private EstadoEnum estado;

    @Nullable
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Nullable
    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

}
