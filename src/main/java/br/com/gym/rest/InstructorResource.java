package br.com.gym.rest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.Instructor;
import br.com.gym.util.JPAUtil;

@Path("/Instructores")
public class InstructorResource {

    private ObjectMapper objectMapper;

    public InstructorResource() {
        this.objectMapper = new ObjectMapper();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarInstructores() throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        String jpql = "SELECT NEW br.com.academia.modelos.Instructor(i.id, c.nome, i.telefone, i.status) FROM Istrutor i";
        Query query = manager.createQuery(jpql);
        List Instructores = query.getResultList();
        manager.getTransaction().commit();
        manager.close();

        return objectMapper.writeValueAsString(Instructores);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String retornarInstructor(@PathParam("id") int id) throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        String jpql = "SELECT i FROM Instructor i JOIN FETCH i.recebimentos";
        Query query = manager.createQuery(jpql);

        Instructor Instructor = (Instructor) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return Instructor.paraJson();
    }

    @POST
    @Path("/novo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarInstructor(String conteudo) throws IOException {
        Instructor i = objectMapper.readValue(conteudo, Instructor.class);

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        manager.persist(i);
        manager.getTransaction().commit();
        manager.close();

        URI location = URI.create("/Instructores/" + i.getId());
        return Response.created(location).build();
    }

    @PUT
    @Path("/{id}/altera")
    @Produces(MediaType.APPLICATION_XML)
    public Response alterarInstructor(String conteudo) throws IOException {
        Instructor Instructor = objectMapper.readValue(conteudo, Instructor.class);

        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        Instructor i = manager.find(Instructor.class, Instructor.getId());

        i.setNome(Instructor.getNome());
        i.setStatus(Instructor.getStatus());
        i.setTelefone(Instructor.getTelefone());

        manager.getTransaction().commit();
        manager.close();
        URI location = URI.create("/exercicios/" + i.getId());
        return Response.created(location).build();
    }


}
