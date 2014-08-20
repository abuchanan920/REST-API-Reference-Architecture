package com.hibu.restreference;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hibu.etcd.ConfigurationServlet;
import com.hibu.restreference.health.SampleObjectPoolHealthCheck;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.resources.SampleResource;
import com.hibu.swagger.SwaggerBundle;

/**
 * Main Application class
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleApplication extends Application<SampleConfiguration> {
	public static final String API_VERSION = "1.0.0";
	
	// NOTE: Should use your dependency injection framework of choice here
	private SampleRepository repository; 
	
	SwaggerBundle swaggerBundle = new SwaggerBundle(API_VERSION);


	public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }
	
	@Override
    public String getName() {
        return "rest-reference";
    }
	
	@Override
	public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
		// Configure Swagger
		bootstrap.addBundle(swaggerBundle);
	}

	@Override
	public void run(SampleConfiguration configuration,
			Environment environment) throws Exception {
		
		repository = (SampleRepository) Class.forName(configuration.getSampleRepository()).newInstance();
		
		// Wire up the SampleResource
		final SampleResource sampleResource = new SampleResource(repository);
		environment.jersey().register(sampleResource);
		
		// Wire up the SampleRepository health check
		final SampleObjectPoolHealthCheck websitePoolHealthCheck = new SampleObjectPoolHealthCheck(repository);
		environment.healthChecks().register("sampleobject-pool", websitePoolHealthCheck);
		
		// Add a servlet to display effective configuration
		environment.admin().addServlet("Configuration", new ConfigurationServlet(configuration)).addMapping("/config");
		
		// Configure swagger
		swaggerBundle.configureBasePath(configuration.getSwaggerBasePath());
	}

}
