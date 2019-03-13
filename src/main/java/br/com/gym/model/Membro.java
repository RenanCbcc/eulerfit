package br.com.gym.model;

import javax.persistence.*;

import br.com.gym.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public abstract class Membro {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @JsonIgnore
    @Version
    private Integer versao;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Modalidade> modalidades;

    Membro(Integer id, String nome, String telefone, Status status) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.status = status;
        this.modalidades = new HashSet<>();

    }

    Membro(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        this.status = Status.ATIVO;
        this.modalidades = new HashSet<>();
    }

    Membro(Integer id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.status = Status.ATIVO;
        this.modalidades = new HashSet<>();

    }

    Membro() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public String paraJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public void addModalidade(Modalidade modalidade) {
        this.modalidades.add(modalidade);
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public int valorTotalModalidades() {
        int soma = 0;
        for (Modalidade m : this.modalidades) {
            soma = +m.getPreco();
        }
        return soma;
    }

    @Override
    public String toString() {
        return "Membro{" +
                "Id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", status=" + status +
                ", versao=" + versao +
                '}';
    }
}
