package br.com.nova.api.escola.dtos.pessoa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "DTO para alteração do nome da pessoa.")
public class PessoaNomeChangeRequest {

    @NotBlank(message = "{pessoa.nome.vazio}")
    @Size(max = 255, message = "{pessoa.nome.tamanho}")
    @ApiModelProperty(notes = "Novo nome para a pessoa")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
