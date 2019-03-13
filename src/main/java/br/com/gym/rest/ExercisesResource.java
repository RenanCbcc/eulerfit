package br.com.gym.rest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gym.model.Exercise;
import br.com.gym.util.JPAUtil;

@Path("/exercicios")
public class ExercisesResource {

    private ObjectMapper objectMapper;

    public ExercisesResource() {
        this.objectMapper = new ObjectMapper();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarExercicios() throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        String jpql = "SELECT e FROM Exercicio e";
        Query query = manager.createQuery(jpql);
        List exercicios = query.getResultList();

        manager.getTransaction().commit();
        manager.close();

        return objectMapper.writeValueAsString(exercicios);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarExercicio(@PathParam("id") int id) throws JsonProcessingException {
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        String jpql = "SELECT e FROM Exercicio e WHERE e.id = :pExercicio";
        Query query = manager.createQuery(jpql);
        query.setParameter("pExercicio", id);
        Exercise exercicio = (Exercise) query.getSingleResult();

        manager.getTransaction().commit();
        manager.close();

        return exercicio.paraJson();
    }

    @PUT
    @Path("/{id}/altera")
    @Produces(MediaType.APPLICATION_XML)
    public Response alterarExercicio(String conteudo) throws IOException {
        Exercise exercicio = objectMapper.readValue(conteudo, Exercise.class);
        EntityManager manager = new JPAUtil().getEntityManager();
        manager.getTransaction().begin();

        Exercise e = manager.find(Exercise.class, exercicio.getId());
        e.setNome(exercicio.getNome());
        e.setSeries(exercicio.getSeries());
        e.setRepeticoes(exercicio.getRepeticoes());
        e.setCarga(exercicio.getCarga());

        manager.getTransaction().commit();
        manager.close();
        URI location = URI.create("/exercicios/" + exercicio.getId());
        return Response.created(location).build();
    }

}
