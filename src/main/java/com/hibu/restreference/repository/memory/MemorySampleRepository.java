package com.hibu.restreference.repository.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hibu.restreference.core.SampleObjectBean;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.repository.SampleRepositoryException;

public class MemorySampleRepository implements SampleRepository {
	private final Map<UUID,SampleObjectBean> objects = new HashMap<>();

	@Override
	public UUID addSampleObject(SampleObjectBean sampleObject) {
		sampleObject.setId(UUID.randomUUID());
		updateSampleObject(sampleObject);
		return sampleObject.getId();
	}

	@Override
	public SampleObjectBean getSampleObject(UUID id)
			throws SampleRepositoryException {
		return objects.get(id);
	}

	@Override
	public List<SampleObjectBean> getAllSampleObjects()
			throws SampleRepositoryException {
		List<SampleObjectBean> results = new ArrayList<>();
		results.addAll(objects.values());
		return results;
	}

	@Override
	public int getSampleObjectCount() {
		return objects.size();
	}

	@Override
	public void updateSampleObject(SampleObjectBean sampleObject) {
		objects.put(sampleObject.getId(), sampleObject);
	}

	@Override
	public void deleteSampleObject(UUID id) {
		objects.remove(id);
	}

}
