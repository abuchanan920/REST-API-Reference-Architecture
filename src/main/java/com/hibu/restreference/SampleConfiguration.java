package com.hibu.restreference;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hibu.etcd.EtcdConfiguration;

/**
 * This demonstrates how to wire up a config setting. You could store database connection info here for example.
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleConfiguration extends EtcdConfiguration {
	private static final String BASE_PATH = "/restreference";
	private static final String SETTING_SAMPLE_CONFIG = "sampleConfigSetting";
	private static final String SETTING_SWAGGER_BASE_PATH = "swaggerBasePath";
	
	@NotEmpty
	private String sampleConfigSetting;
	
	private String swaggerBasePath;
	
	public SampleConfiguration() {
		super(BASE_PATH);
	}

	@JsonProperty
	public String getSampleConfigSetting() {
		// CAUTION: This will effectively check with etcd everytime the setting is accessed.
		// Depending on your needs, you may want to implement a caching scheme for the settings so as not to check every time.
		// Or better yet, read once and then listen for updates.
		String result = getSetting(SETTING_SAMPLE_CONFIG);
		if (result == null) {
			result = sampleConfigSetting;
		}
		return result;
	}

	@JsonProperty
	public void setSampleConfigSetting(String setting) {
		this.sampleConfigSetting = setting;
	}
	
	@JsonProperty
	public String getSwaggerBasePath() {
		// CAUTION: This will effectively check with etcd everytime the setting is accessed.
		// Depending on your needs, you may want to implement a caching scheme for the settings so as not to check every time.
		// Or better yet, read once and then listen for updates.
		String result = getSetting(SETTING_SWAGGER_BASE_PATH);
		if (result == null) {
			result = swaggerBasePath;
		}
		return result;
	}
	
	@JsonProperty
	public void setSwaggerBasePath(String setting) {
		this.swaggerBasePath = setting;
	}
	
}
