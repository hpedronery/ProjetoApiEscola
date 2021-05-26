package br.com.nova.api.escola.model;

import br.com.nova.api.escola.enums.EstadoEnum;

import javax.persistence.*;

@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cidade_id", nullable = false)
    private long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnum estado;

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

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }
}
