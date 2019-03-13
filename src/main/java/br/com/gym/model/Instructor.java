package br.com.gym.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.gym.model.enums.Status;


@Entity
public class Instructor extends Membro {

    private String email;
    private String senha;

    /*
     * The one that gets serialized normally.*/
    @JsonBackReference(value = "instrutor-recebimentos")
    @OneToMany(mappedBy = "instrutor", cascade = CascadeType.PERSIST)
    private List<Payment> recebimentos;

    public Instructor() {
        super();
    }

    public Instructor(Integer id, String nome, String telefone) {
        super(id, nome, telefone);
        this.recebimentos = new ArrayList<>();
    }

    public Instructor(Integer id, String nome, String telefone, String email,
                     String senha, Status status) {
        super(id, nome, telefone, status);
        this.email = email;
        this.senha = senha;
        this.recebimentos = new ArrayList<>();
    }

    public Instructor(String nome, String telefone, String email, String senha) {
        super(nome, telefone);
        this.email = email;
        this.senha = senha;
        this.recebimentos = new ArrayList<>();
        this.setStatus(Status.ATIVO);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Payment> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<Payment> recebimentos) {
        this.recebimentos = recebimentos;
    }

    public void receber(Payment pagamento) {
        this.recebimentos.add(pagamento);
        pagamento.setInstrutor(this);
    }

    @Override
    public String toString() {
        return "Instrutor{" +
                "email='" + email + '\'' +
                '}';
    }
}
