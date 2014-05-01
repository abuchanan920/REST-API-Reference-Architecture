package com.hibu.restreference.core;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A sample domain object.
 * 
 * Demonstrates using UUID's as the primary ID. 
 * This can be useful in a distributed system to avoid the need for a centralized id issuing system (database or otherwise).
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
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

	@JsonProperty
	public void setId(UUID id) {
		this.id = id;
	}
	
	@JsonProperty
	public UUID getId() {
		return id;
	}

	@JsonProperty
	public long getValue() {
		return value;
	}

	@JsonProperty
	public String getMessage() {
		return message;
	}
	
}
