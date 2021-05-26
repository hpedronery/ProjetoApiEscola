package br.com.nova.api.escola.dtos.pessoa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@ApiModel(value = "DTO para alteração da data de nascimento de uma pessoa.")
public class PessoaDataNascimentoChangeRequest {

    @ApiModelProperty(notes = "Nova data de nascimento")
    private LocalDate dataNascimento;

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
