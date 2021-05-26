package br.com.nova.api.escola.dtos.cidade;

import br.com.nova.api.escola.enums.EstadoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ApiModel(value = "DTO para alteração do estado da cidade.")
public class CidadeEstadoChangeRequest {

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Novo estado para a cidade")
    private EstadoEnum estado;

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }
}
