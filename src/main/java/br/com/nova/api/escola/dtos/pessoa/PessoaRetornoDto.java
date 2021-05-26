package br.com.nova.api.escola.dtos.pessoa;

import br.com.nova.api.escola.enums.CorpoEnum;
import br.com.nova.api.escola.enums.SexoEnum;
import br.com.nova.api.escola.model.Escola;

public class PessoaRetornoDto {

    private String nome;

    private long id;

    private Escola escola;

    public Escola getEscola() {
        return escola;
    }

    private SexoEnum sexo;

    private CorpoEnum corpo;

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

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
