package br.com.nova.api.escola.dtos.escola;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "DTO para alteração do nome da escola.")
public class EscolaNomeChangeRequest {

    @NotBlank(message = "{escola.nome.vazio}")
    @Size(max = 255, message = "{escola.nome.tamanho}")
    @ApiModelProperty(notes = "Novo nome para a escola")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
