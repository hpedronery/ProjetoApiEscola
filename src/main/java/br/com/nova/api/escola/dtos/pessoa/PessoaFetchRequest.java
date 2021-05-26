package br.com.nova.api.escola.dtos.pessoa;

import br.com.nova.api.escola.enums.CorpoEnum;
import br.com.nova.api.escola.enums.SexoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;

@ApiModel(value = "Dto para filtrar a busca de pessoas")
public class PessoaFetchRequest {

    @ApiModelProperty(notes = "Filtra a lista por nome")
    private String nome;

    @ApiModelProperty(notes = "Filtra a lista pelo sexo")
    private SexoEnum sexo;

    @ApiModelProperty(notes = "Filtra a lista pelo sexo")
    private CorpoEnum corpo;

    @Nullable
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Nullable
    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    @Nullable
    public CorpoEnum getCorpo() {
        return corpo;
    }

    public void setCorpo(CorpoEnum corpo) {
        this.corpo = corpo;
    }
}