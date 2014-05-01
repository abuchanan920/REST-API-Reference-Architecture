package com.hibu.restreference;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class SampleConfiguration extends Configuration {
	@NotEmpty
	private String sampleConfigSetting;

	@JsonProperty
	public String getSampleConfigSetting() {
		return sampleConfigSetting;
	}

	@JsonProperty
	public void setSampleConfigSetting(String databaseServer) {
		this.sampleConfigSetting = databaseServer;
	}
	
}
