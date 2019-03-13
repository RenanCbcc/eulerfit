package br.com.gym.util;

import java.io.IOException;
import java.util.Calendar;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.Client;
import br.com.gym.model.Instructor;
import br.com.gym.model.Payment;
import br.com.gym.model.enums.Modo;

public class TestJPQL {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Calendar data = Calendar.getInstance();
        data.set(Calendar.YEAR, 1995);
        data.set(Calendar.MONTH, Calendar.MARCH);
        data.set(Calendar.DAY_OF_MONTH, 20);

        Client caio = new Client(3, "Caio bojosa", "00838850219", Calendar.getInstance());
        Instructor euler = new Instructor(2, "Euler euler", "00838850219");


        Payment mensalidadeCaio = new Payment(Calendar.getInstance(), 80, Modo.CREDITO);

        caio.pagar(mensalidadeCaio);
        euler.receber(mensalidadeCaio);

        String cliente_json = mensalidadeCaio.paraJson();

        Payment p = objectMapper.readValue(cliente_json, Payment.class);

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();


        Client c = manager.find(Client.class,p.getCliente().getId());
        Instructor i = manager.find(Instructor.class,p.getInstrutor().getId());

        c.pagar(p);
        i.receber(p);

        manager.getTransaction().commit();
        manager.close();

    }
}
