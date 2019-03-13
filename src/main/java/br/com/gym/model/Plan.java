package br.com.gym.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.enums.Tipo;


@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Version
    private Integer versao;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercicios;


    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Client cliente;

    public Integer getId() {
        return id;
    }

    public Plan() {
    }

    public Plan(Integer id, Tipo tipo, Integer versao, List<Exercise> exercicios) {
        this.id = id;
        this.tipo = tipo;
        this.versao = versao;
        this.exercicios = exercicios;
    }

    public Plan(Tipo tipo) {
        this.tipo = tipo;
        this.exercicios = new ArrayList<>();
    }

    public Plan(Integer id, Tipo tipo) {
        this.id = id;
        this.tipo = tipo;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public List<Exercise> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercise> exercicios) {
        this.exercicios = exercicios;
    }

    public void addExercicio(List<Exercise> exercicios) {
        for (Exercise e : exercicios) {
            this.exercicios.add(e);
            e.setPlano(this);

        }

    }

    public void addExercicio(Exercise exercicio) {
        this.exercicios.add(exercicio);
        exercicio.setPlano(this);
    }

    public String paraJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public Integer getVersao() {
        return versao;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }
}
