package br.com.gym.model;

import java.util.Calendar;

import javax.persistence.*;

import br.com.gym.model.enums.Modo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Calendar data;

    /*It will be omitted from serialization.*/
    //@JsonManagedReference(value = "pagamento-instrutor")
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Instructor instrutor;

    /*It will be omitted from serialization.*/
    //@JsonManagedReference(value = "pagamento-cliente")
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Client cliente;

    @Enumerated(EnumType.STRING)
    private Modo modo;

    private int valor;

    public Payment() {

    }

    public Payment(Calendar data, int valor, Modo modo) {
        this.data = data;
        this.modo = modo;
        this.valor = valor;
    }

    public Payment(Integer id, Calendar data, int valor, Modo modo) {
        this.id = id;
        this.data = data;
        this.modo = modo;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }

    public Instructor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instructor instrutor) {
        this.instrutor = instrutor;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }


    public String paraJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
