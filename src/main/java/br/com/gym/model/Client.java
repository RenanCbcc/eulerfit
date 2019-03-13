package br.com.gym.model;

import java.util.*;

import javax.persistence.*;

import br.com.gym.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Client extends Membro {

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar nasimento;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private List<Plan> planos;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar vencimento;

    /*
     * The one that gets serialized normally.*/
    @JsonBackReference(value = "cliente-pagamentos")
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private List<Payment> pagamentos;


    public Client(String nome, Calendar nasimento, String telefone, Address endereco, Calendar vencimento) {
        super(nome, telefone);
        this.planos = new ArrayList<>();
        this.vencimento = vencimento;
        this.pagamentos = new ArrayList<>();
        this.nasimento = nasimento;
        this.endereco = endereco;
    }

    public Client(Integer id, String nome, Calendar nasimento, String telefone, Address endereco, Status status,
                   Calendar vencimento) {
        super(id, nome, telefone, status);
        this.nasimento = nasimento;
        this.vencimento = vencimento;
        this.endereco = endereco;
        this.planos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();

    }

    public Client(Integer id, String nome, String telefone, Calendar vencimento) {
        super(id, nome, telefone);
        this.vencimento = vencimento;
        this.planos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }


    public Client() {
        super();

    }

    public List<Plan> getPlanos() {
        return planos;
    }

    public void setPlanos(List<Plan> planos) {
        this.planos = planos;
    }

    public List<Payment> getPagamentos() {
        return pagamentos;
    }

    public void setPayments(List<Payment> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void pagar(Payment pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.setCliente(this);
    }

    public void addPlanos(List<Plan> planos) {
        for (Plan p : planos) {
            this.planos.add(p);
            p.setCliente(this);

        }

    }

    public void addPlano(Plan plano) {
        this.planos.add(plano);
        plano.setCliente(this);
    }


    public Calendar getNasimento() {
        return nasimento;
    }

    public void setNasimento(Calendar nasimento) {
        this.nasimento = nasimento;
    }

    public Calendar getVencimento() {
        return vencimento;
    }

    public void setVencimento(Calendar vencimento) {
        this.vencimento = vencimento;
    }

    public Address getEndereco() {
        return endereco;
    }

    public void setEndereco(Address endereco) {
        this.endereco = endereco;
    }


}
