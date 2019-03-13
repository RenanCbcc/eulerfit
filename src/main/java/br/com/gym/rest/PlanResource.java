package br.com.gym.rest;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.Client;
import br.com.gym.model.Plan;
import br.com.gym.util.JPAUtil;

@Path("/planos")
public class PlanResource {

    private ObjectMapper objectMapper;

    public PlanResource() {
        this.objectMapper = new ObjectMapper();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarPlanos() throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        String jpql = "SELECT DISTINCT p FROM Plano p JOIN FETCH p.exercicios";
        Query query = manager.createQuery(jpql, Plan.class);
        List planos = query.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return objectMapper.writeValueAsString(planos);

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarPlano(@PathParam("id") int id) throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        String jpql = "SELECT p FROM Plano p JOIN FETCH p.exercicios WHERE p.id = :pPlano";
        Query query = manager.createQuery(jpql);
        query.setParameter("pPlano", id);
        Plan plano = (Plan) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return plano.paraJson();
    }


    @POST
    @Path("/{clienteId}/novo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarCliente(@PathParam("clienteId") int clienteId, String conteudo) throws IOException {
        List<Plan> planos = objectMapper.readValue(conteudo, new TypeReference<List<Plan>>() {
        });

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();
        Client c = manager.find(Client.class, clienteId);
        c.addPlanos(planos);

        manager.getTransaction().commit();
        manager.close();
        return Response.ok().status(201).build();
    }

    @DELETE
    @Path("/{id}/clientes/{clienteId}/remove")
    @Produces(MediaType.APPLICATION_XML)
    public Response removePlanoDoCliente(@PathParam("id") int id, @PathParam("clienteId") int clienteId) {
        Response response = null;
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        Client c = manager.find(Client.class, clienteId);
        Plan p = manager.find(Plan.class, id);
        if (c.getPlanos().remove(p)) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        manager.remove(p);
        manager.getTransaction().commit();
        manager.close();

        return response;
    }


}
