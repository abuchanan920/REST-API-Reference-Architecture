package com.hibu.restreference;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hibu.restreference.health.SampleObjectPoolHealthCheck;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.repository.memory.MemorySampleRepository;
import com.hibu.restreference.resources.SampleResource;

public class SampleApplication extends Application<SampleConfiguration> {
	private final SampleRepository repository = new MemorySampleRepository(); // should use your dependency injection framework of choice here

	public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }
	
	@Override
    public String getName() {
        return "rest-reference";
    }
	
	@Override
	public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(SampleConfiguration configuration,
			Environment environment) throws Exception {
		final SampleResource websiteResource = new SampleResource(repository);
		environment.jersey().register(websiteResource);
		
		final SampleObjectPoolHealthCheck websitePoolHealthCheck = new SampleObjectPoolHealthCheck(repository);
		environment.healthChecks().register("website-pool", websitePoolHealthCheck);
	}

}
