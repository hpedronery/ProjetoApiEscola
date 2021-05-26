package br.com.nova.api.escola.dtos.cidade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "DTO para alteração do nome da cidade.")
public class CidadeNomeChangeRequest {

    @NotBlank(message = "{cidade.nome.vazio}")
    @Size(max = 255, message = "{cidade.nome.tamanho}")
    @ApiModelProperty(notes = "Novo nome para a cidade")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
