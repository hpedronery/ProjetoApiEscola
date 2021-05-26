package br.com.nova.api.escola.dtos.escola;

public class EscolaRetornoDto {

    private String nome;

    private long id;

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