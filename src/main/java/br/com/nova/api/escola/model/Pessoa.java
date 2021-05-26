package br.com.nova.api.escola.model;

import br.com.nova.api.escola.enums.CorpoEnum;
import br.com.nova.api.escola.enums.SexoEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pessoa_id", nullable = false)
    private long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sexo", nullable = false)
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Column(name = "corpo", nullable = false)
    @Enumerated(EnumType.STRING)
    private CorpoEnum corpo;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "escola_id")
    private Escola escola;

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }
}
