package com.hibu.restreference.core;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * A sample domain object.
 * 
 * Demonstrates using UUID's as the primary ID. 
 * This can be useful in a distributed system to avoid the need for a centralized id issuing system (database or otherwise).
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
@ApiModel(value = "This is a sample object with a few fields")
public class SampleObjectBean {
	
	private UUID id;
	private long value;
	private String message;
	
	public SampleObjectBean() {}
	
	public SampleObjectBean(UUID id, long value, String message) {
		this.id = id;
		this.value = value;
		this.message = message;
	}

	@ApiModelProperty(value = "The object ID")
	@JsonProperty
	public void setId(UUID id) {
		this.id = id;
	}
	
	@JsonProperty
	public UUID getId() {
		return id;
	}

	@ApiModelProperty(value = "A sample numeric value", required=true)
	@JsonProperty
	public long getValue() {
		return value;
	}

	@ApiModelProperty(value = "A message to go along with the object", required = false)
	@JsonProperty
	public String getMessage() {
		return message;
	}
	
}
