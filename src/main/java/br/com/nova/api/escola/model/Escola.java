package br.com.nova.api.escola.model;

import br.com.nova.api.escola.enums.RedeEscolaEnum;

import javax.persistence.*;

@Entity
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "escola_id", nullable = false)
    private long id;

    @Column(name = "escola", nullable = false)
    private String nome;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    private RedeEscolaEnum redeEscola;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public RedeEscolaEnum getRedeEscola() {
        return redeEscola;
    }

    public void setRedeEscola(RedeEscolaEnum redeEscola) {
        this.redeEscola = redeEscola;
    }
}
