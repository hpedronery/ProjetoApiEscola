package br.com.nova.api.escola.enums;

public enum CorpoEnum {

    CORPO_DOCENTE("Corpo Docente"),
    CORPO_DISCENTE("Corpo Discente");

    private final String nome;

    CorpoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
