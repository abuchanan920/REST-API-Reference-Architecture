package com.hibu.restreference.health;

import com.codahale.metrics.health.HealthCheck;
import com.hibu.restreference.repository.SampleRepository;

public class SampleObjectPoolHealthCheck extends HealthCheck {
	private final SampleRepository repository;
	
	public SampleObjectPoolHealthCheck(SampleRepository repository) {
		this.repository = repository;
	}

	@Override
	protected Result check() throws Exception {
		// Pretend we can only store 3 websites and more is an unhealthy condition
		if (repository.getSampleObjectCount() > 3) {
			return Result.unhealthy("More than the max (3) number of objects stored!");
		}
		return Result.healthy();
	}

}
