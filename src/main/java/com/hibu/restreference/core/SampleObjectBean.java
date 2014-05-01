package com.hibu.restreference.core;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

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
