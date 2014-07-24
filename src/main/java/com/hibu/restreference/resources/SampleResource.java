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
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * Demonstrates mapping of URLs to application logic
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
@Path("resources")
@Api(
		value = "/resources", 
		description = "Operations on sample resources"
)
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {
	@Context UriInfo uriInfo;
	
	private final SampleRepository repository;
	
	public SampleResource(SampleRepository repository) {
		this.repository = repository;
	}
	
	@GET
	@Timed
	@ApiOperation(
			value = "Fetch all defined sample objects", 
			response = SampleObjectBean.class, 
			responseContainer = "List"
	)
	public List<SampleObjectBean> getResources() throws SampleRepositoryException {
		return repository.getAllSampleObjects();
	}
	
	@GET
	@Path("/{id}")
	@ApiOperation(
			value = "Find a sample object by ID", 
			notes = "More notes about this method", 
			response = SampleObjectBean.class
	)
	@ApiResponses(value = {
	  @ApiResponse(code = 400, message = "Invalid ID supplied"),
	  @ApiResponse(code = 404, message = "Sample resource not found") 
	})
	@Timed
	public SampleObjectBean getResource(
			@ApiParam(value = "The id of the sample bean to fetch", required = true) @PathParam("id") String id) throws SampleRepositoryException {
		return repository.getSampleObject(UUID.fromString(id));
	}
	
	@PUT
	@Path("/{id}")
	@ApiOperation(
			value = "Define a sample object with a specific ID", 
			notes = "Any ID sent in the JSON will be ignored in favor of the one specified in the URI"
	)
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	public Response putResource(
			@ApiParam(value = "The id of the sample bean to save", required = true) @PathParam("id") String id, SampleObjectBean object) throws SampleRepositoryException {
		object.setId(UUID.fromString(id));
		repository.updateSampleObject(object);
		return Response.ok().build();
	}
	
	@POST
	@ApiOperation(value = "Define a new sample object")
	@ApiResponses(value = {
			  @ApiResponse(code = 204, message = "Created"),
			  @ApiResponse(code = 400, message = "Invalid data") 
			})
	@Consumes(MediaType.APPLICATION_JSON)
	@Timed
	public Response postResource(SampleObjectBean object) throws SampleRepositoryException {
		UUID id = repository.addSampleObject(object);
		URI websiteUri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
		return Response.created(websiteUri).build();
	}
	
}
