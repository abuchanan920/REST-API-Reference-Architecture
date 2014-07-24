package com.hibu.swagger;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.converter.OverrideConverter;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Environment;

public class SwaggerBundle extends AssetsBundle {
	  public static final String DEFAULT_PATH = "/docs";
	  public String apiVersion = "1.0.0";
	  public String basePath = "http://localhost:8080";

	  public SwaggerBundle(String apiVersion) {
	    super(DEFAULT_PATH, DEFAULT_PATH, "index.html", "assets");
	    this.apiVersion = apiVersion;
	  }

	  @Override
	  public void run(Environment environment) {
	    super.run(environment);
	    // Initialize format overrides
		configureUUIDConverter();
	    
		// Swagger Resource
		environment.jersey().register(new ApiListingResourceJSON());

	    // Swagger providers
		environment.jersey().register(new ApiDeclarationProvider());
		environment.jersey().register(new ResourceListingProvider());

	    // Swagger Scanner, which finds all the resources for @Api Annotations
	    ScannerFactory.setScanner(new DefaultJaxrsScanner());

	    // Add the reader, which scans the resources and extracts the resource information
	    ClassReaders.setReader(new DefaultJaxrsApiReader());

	    // Set the swagger config options
	    SwaggerConfig config = ConfigFactory.config();
	    config.setApiVersion(apiVersion);
	  }
	  
	  public void configureBasePath(String basePath) {
		    SwaggerConfig config = ConfigFactory.config();
		    config.setBasePath(basePath);
	  }

	private void configureUUIDConverter() {
		// A demonstration of how to override the serialization info for a class for Swagger docs. Normally UUID would display as two numbers.
		String jsonString = "{ \"id\": \"UUID\", \"properties\": { \"value\": { \"type\": \"string\" } } }";
		
		OverrideConverter converter = new OverrideConverter();
		converter.add("java.util.UUID", jsonString);
		
		ModelConverters.addConverter(converter, true);
	}
}
