package com.hibu.restreference.repository;

import java.util.List;
import java.util.UUID;

import com.hibu.restreference.core.SampleObjectBean;


public interface SampleRepository {
	public UUID addSampleObject(SampleObjectBean sampleObject) throws SampleRepositoryException;
	
	public List<SampleObjectBean> getAllSampleObjects() throws SampleRepositoryException;
	public SampleObjectBean getSampleObject(UUID id) throws SampleRepositoryException;
	public int getSampleObjectCount() throws SampleRepositoryException;

	public void updateSampleObject(SampleObjectBean sampleObject) throws SampleRepositoryException;

	public void deleteSampleObject(UUID id) throws SampleRepositoryException;
}
