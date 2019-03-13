package br.com.gym.model;

import br.com.gym.model.enums.Categoria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;

@Entity
public class Modalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private int preco;

    public Modalidade(int id, Categoria categoria, int preco) {
        this.id = id;
        this.categoria = categoria;
        this.preco = preco;

    }

    public Modalidade(Categoria categoria, int preco) {

        this.categoria = categoria;
        this.preco = preco;

    }

    public Modalidade() {
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String paraJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }


}
