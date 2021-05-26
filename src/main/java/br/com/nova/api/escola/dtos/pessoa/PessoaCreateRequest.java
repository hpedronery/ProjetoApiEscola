package br.com.nova.api.escola.dtos.pessoa;

import br.com.nova.api.escola.enums.CorpoEnum;
import br.com.nova.api.escola.enums.SexoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel(value = "Dto para criar uma pessoa")
public class PessoaCreateRequest {

    @NotBlank(message = "{pessoa.nome.vazio}")
    @Size(max = 255, message = "{pessoa.nome.tamanho}")
    @ApiModelProperty(notes = "O nome da pessoa.")
    private String nome;

    @NotNull(message = "{pessoa.sexo.vazia}")
    @ApiModelProperty(notes = "O sexo da pessoa.")
    private SexoEnum sexo;

    @NotNull(message = "{pessoa.corpo.vazio}")
    @ApiModelProperty(notes = "O corpo escolar da pessoa.")
    private CorpoEnum corpo;

    @NotNull(message = "{pessoa.escola.id.vazio}")
    private long escolaId;

    @NotNull(message = "{pessoa.data.nascimento.vazio}")
    private LocalDate dataNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public CorpoEnum getCorpo() {
        return corpo;
    }

    public void setCorpo(CorpoEnum corpo) {
        this.corpo = corpo;
    }

    public long getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(long escolaId) {
        this.escolaId = escolaId;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
