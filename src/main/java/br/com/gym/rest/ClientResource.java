package br.com.gym.rest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.gym.model.Client;
import br.com.gym.model.Payment;
import br.com.gym.model.Plan;
import br.com.gym.util.JPAUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/Clientes")
public class ClientResource {

	private ObjectMapper objectMapper;

	public ClientResource() {
		this.objectMapper = new ObjectMapper();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listarClientes() throws JsonProcessingException {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		String jpql = "SELECT NEW Cliente(c.id, c.nome, c.nasimento,c.telefone, "
				+ "c.endereco, c.status,c.vencimento) FROM Cliente c";

		Query query = manager.createQuery(jpql, Client.class);
		List Clientes = query.getResultList();
		manager.getTransaction().commit();
		manager.close();
		return objectMapper.writeValueAsString(Clientes);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String retornarCliente(@PathParam("id") int id) throws JsonProcessingException {
		// Esta consulta poderia ser mais simples. Porém eu estava recebemdo o erro
		// org.hibernate.QueryException:
		// query specified join fetching, but the owner of the fetched association was
		// not present in the select list
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();

		String jpql = "SELECT NEW Cliente(c.id, c.nome, c.nasimento,c.telefone, "
				+ "c.endereco, c.status,c.vencimento) FROM Cliente c WHERE c.id = :pCliente";

		Query query = manager.createQuery(jpql, Client.class);
		query.setParameter("pCliente", id);

		Client c = (Client) query.getSingleResult();

		jpql = "SELECT p From Plano p JOIN FETCH p.exercicios WHERE p.Cliente.id = :pCliente";
		query = manager.createQuery(jpql, Plan.class);
		query.setParameter("pCliente", id);

		List<Plan> planos = query.getResultList();
		manager.getTransaction().commit();
		manager.close();
		c.addPlanos(planos);
		return c.paraJson();
	}

	@GET
	@Path("/{id}/Payments")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarPaymentsCliente(@PathParam("id") int id) throws JsonProcessingException {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();

		String jpql = "SELECT NEW Cliente(c.id, c.nome, c.nasimento, c.telefone, c.endereco,c.status,c.vencimento)"
				+ " FROM Cliente c WHERE c.id = :pCliente";

		Query query = manager.createQuery(jpql, Client.class);
		query.setParameter("pCliente", id);

		Client c = (Client) query.getSingleResult();

		jpql = "SELECT New Payment(p.id, p.data, p.valor, p.modo) From Payment p WHERE p.Cliente.id = :pCliente";
		query = manager.createQuery(jpql, Payment.class);
		query.setParameter("pCliente", id);

		List Payments = query.getResultList();
		manager.getTransaction().commit();
		manager.close();

		c.setPayments(Payments);

		return objectMapper.writeValueAsString(c);
	}

	@POST
	@Path("/novo")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response criarCliente(String conteudo) throws IOException {
		Client c = objectMapper.readValue(conteudo, Client.class);

		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();

		manager.persist(c);
		manager.getTransaction().commit();
		manager.close();

		URI location = URI.create("/Clientes/" + c.getId());
		return Response.created(location).build();
	}

	@PUT
	@Path("/{id}/altera")
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarCliente(String conteudo) throws IOException {
		Client c = objectMapper.readValue(conteudo, Client.class);

		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		// TODO implementar a alteração do Cliente
		manager.find(Client.class, c);
		manager.getTransaction().commit();
		manager.close();
		URI local = URI.create("/Clientes/" + c.getId());
		return Response.created(local).build();
	}

}
