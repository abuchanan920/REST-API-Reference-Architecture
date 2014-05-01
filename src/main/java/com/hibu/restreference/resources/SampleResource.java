package com.hibu.restreference.resources;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.codahale.metrics.annotation.Timed;
import com.hibu.restreference.core.SampleObjectBean;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.repository.SampleRepositoryException;

/**
 * Demonstrates mapping of URLs to application logic
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
@Path("resources")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {
	@Context UriInfo uriInfo;
	
	private final SampleRepository repository;
	
	public SampleResource(SampleRepository repository) {
		this.repository = repository;
	}
	
	@GET
	@Timed
	public List<SampleObjectBean> getResources() throws SampleRepositoryException {
		return repository.getAllSampleObjects();
	}
	
	@GET
	@Path("{id}")
	@Timed
	public SampleObjectBean getResource(@PathParam("id") String id) throws SampleRepositoryException {
		return repository.getSampleObject(UUID.fromString(id));
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	public Response putResource(@PathParam("id") String id, SampleObjectBean object) throws SampleRepositoryException {
		object.setId(UUID.fromString(id));
		repository.updateSampleObject(object);
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	public Response postResource(SampleObjectBean object) throws SampleRepositoryException {
		UUID id = repository.addSampleObject(object);
		URI websiteUri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
		return Response.created(websiteUri).build();
	}
	
}
