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
	private static final String SETTING_SAMPLE_REPOSITORY = "sampleRepository";
	
	@NotEmpty
	private String sampleConfigSetting;
	
	private String swaggerBasePath;
	
	private String sampleRepository;
	
	public SampleConfiguration() {
		super(BASE_PATH);
	}

	@JsonProperty
	public String getSampleConfigSetting() {
		String result = getSetting(SETTING_SAMPLE_CONFIG);
		if (result == null) {
			result = sampleConfigSetting;
		} else {
			sampleConfigSetting = result;
		}
		return result;
	}

	@JsonProperty
	public void setSampleConfigSetting(String setting) {
		this.sampleConfigSetting = setting;
	}
	
	@JsonProperty
	public String getSwaggerBasePath() {
		String result = getSetting(SETTING_SWAGGER_BASE_PATH);
		if (result == null) {
			result = swaggerBasePath;
		} else {
			swaggerBasePath = result;
		}
		return result;
	}
	
	@JsonProperty
	public void setSwaggerBasePath(String setting) {
		this.swaggerBasePath = setting;
	}
	
	@JsonProperty
	public String getSampleRepository() {
		String result = getSetting(SETTING_SAMPLE_REPOSITORY);
		if (result == null) {
			result = sampleRepository;
		} else {
			sampleRepository = result;
		}
		return result;
	}

	@JsonProperty
	public void setSampleRepository(String setting) {
		this.sampleRepository = setting;
	}

}
