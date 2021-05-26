package br.com.nova.api.escola.enums;

public enum RedeEscolaEnum {

    EM("Escola Municipal"),
    EE("Escola Estadual"),
    EF("Escola Federal"),
    EP("Escola Privada");

    private final String nome;

    RedeEscolaEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
