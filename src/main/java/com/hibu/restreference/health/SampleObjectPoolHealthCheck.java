package com.hibu.restreference.health;

import com.codahale.metrics.health.HealthCheck;
import com.hibu.restreference.repository.SampleRepository;

/**
 * An example classes used to implement system health checks.
 * 
 * These can then be used by load balancers to remove failed systems from the cluster.
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleObjectPoolHealthCheck extends HealthCheck {
	
	private final SampleRepository repository;
	
	public SampleObjectPoolHealthCheck(SampleRepository repository) {
		this.repository = repository;
	}

	@Override
	protected Result check() throws Exception {
		// Pretend we can only store 3 objects and more is an unhealthy condition
		if (repository.getAddSampleCount() > 3) {
			return Result.unhealthy("More than the max (3) number of objects stored by this process!");
		}
		return Result.healthy();
	}

}
