package com.hibu.restreference;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hibu.restreference.health.SampleObjectPoolHealthCheck;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.repository.memory.MemorySampleRepository;
import com.hibu.restreference.resources.SampleResource;

/**
 * Main Application class
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleApplication extends Application<SampleConfiguration> {
	// NOTE: Should use your dependency injection framework of choice here
	private final SampleRepository repository = new MemorySampleRepository(); 


	public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }
	
	@Override
    public String getName() {
        return "rest-reference";
    }
	
	@Override
	public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
		
	}

	@Override
	public void run(SampleConfiguration configuration,
			Environment environment) throws Exception {
		// Wire up the SampleResource
		final SampleResource sampleResource = new SampleResource(repository);
		environment.jersey().register(sampleResource);
		
		// Wire up the SampleRepository health check
		final SampleObjectPoolHealthCheck websitePoolHealthCheck = new SampleObjectPoolHealthCheck(repository);
		environment.healthChecks().register("sampleobject-pool", websitePoolHealthCheck);
	}

}
