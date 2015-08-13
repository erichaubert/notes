package com.ehaubert.note;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/notes")
public class NoteResource{
	private static final Logger LOG = Logger.getLogger(NoteResource.class.getName());
	
	//in real world, I wouldn't manage my transactions by hand like this
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("notePU");
	
	private EntityManager getEntityManager(){
		if(emf == null){
			emf = Persistence.createEntityManagerFactory("notePU");
		}
		return emf.createEntityManager();
	}
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response find(@PathParam("id")String idAsString){
		LOG.info("GET id:"+idAsString );
		EntityManager em = getEntityManager();
		Integer id = Integer.valueOf(idAsString);
		NoteEntity note = em.find(NoteEntity.class, id);
		return Response.ok(note).build();
	}
	@GET
	@Produces("application/json")
	public Response search(@QueryParam("query")String query){
		LOG.info("GET query:"+query);
		EntityManager em = getEntityManager();
		
		List<NoteEntity> results = null;
		if(query == null || query.isEmpty()){
			results = em.createNamedQuery("NoteEntity.findAll", NoteEntity.class).getResultList();
		}else{
			TypedQuery<NoteEntity> emQuery = em.createNamedQuery("NoteEntity.search", NoteEntity.class);
			emQuery.setParameter("criteria", "%" + query + "%");
			results = emQuery.getResultList();
		}
		return Response.ok(new GenericEntity<List<NoteEntity>>(results, List.class)).build();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response create(NoteEntity note){
		LOG.info("POST (missing content-type): "+ note.getBody());
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(note);
		em.getTransaction().commit();
		return Response.ok(note).build();
	}
	@POST
	@Produces("application/json")
	public Response create(String noteAsString){
		LOG.info("POST (missing content-type): ");
		NoteEntity entity;
		try {
			entity = new ObjectMapper().readValue(noteAsString, NoteEntity.class);
		} catch (IOException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Received invalid json note: "+noteAsString).build();
		}
		return create(entity);
	}
}