package com.hibu.restreference.repository;

import java.util.List;
import java.util.UUID;

import com.hibu.restreference.core.SampleObjectBean;

/**
 * A repository interface pattern is useful to allow various implementations to be swapped in for testing, etc.
 * 
 * The methods here should be for the various high level operations an application would want to perform.
 * A specialized exception is defined for the interface to wrap any exceptions thrown by the underlying implementations.
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public interface SampleRepository {

	public UUID addSampleObject(SampleObjectBean sampleObject) throws SampleRepositoryException;
	
	public List<SampleObjectBean> getAllSampleObjects() throws SampleRepositoryException;
	public SampleObjectBean getSampleObject(UUID id) throws SampleRepositoryException;
	public int getSampleObjectCount() throws SampleRepositoryException;

	public void updateSampleObject(SampleObjectBean sampleObject) throws SampleRepositoryException;

	public void deleteSampleObject(UUID id) throws SampleRepositoryException;
	
	public int getAddSampleCount();
}
