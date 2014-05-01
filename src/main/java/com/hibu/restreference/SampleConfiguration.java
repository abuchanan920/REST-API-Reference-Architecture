package com.hibu.restreference;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

/**
 * This demonstrates how to wire up a config setting. You could store database connection info here for example.
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleConfiguration extends Configuration {

	@NotEmpty
	private String sampleConfigSetting;

	@JsonProperty
	public String getSampleConfigSetting() {
		return sampleConfigSetting;
	}

	@JsonProperty
	public void setSampleConfigSetting(String setting) {
		this.sampleConfigSetting = setting;
	}
	
}
