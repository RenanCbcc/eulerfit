package br.com.gym.rest;


import java.io.IOException;
import java.net.URI;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.Client;
import br.com.gym.model.Instructor;
import br.com.gym.model.Payment;
import br.com.gym.util.JPAUtil;

@Path("/pagamentos")
public class PaymentResource {

    private ObjectMapper objectMapper;

    public PaymentResource() {
        this.objectMapper = new ObjectMapper();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String retornarPagamento(@PathParam("id") int id) throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        Payment p = manager.find(Payment.class, id);
        return p.paraJson();

    }


    @POST
    @Path("/novo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarPagamento(String conteudo) throws IOException {
    	Payment p = objectMapper.readValue(conteudo, Payment.class);

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();


        Client c = manager.find(Client.class, p.getCliente().getId());
        Instructor i = manager.find(Instructor.class, p.getInstrutor().getId());

        c.pagar(p);
        i.receber(p);

        manager.getTransaction().commit();
        manager.close();

        URI location = URI.create("/pagamentos/" + p.getId());
        return Response.created(location).build();
    }
}
