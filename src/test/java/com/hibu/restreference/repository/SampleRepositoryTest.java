package com.hibu.restreference.repository;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import com.hibu.restreference.core.SampleObjectBean;

public abstract class SampleRepositoryTest {
	protected SampleRepository repo;

	@Test
	public void simpleTest() throws SampleRepositoryException {
		SampleObjectBean testBean = new SampleObjectBean(null, 2, "Testing 1-2-3");
		UUID newUUID = repo.addSampleObject(testBean);
		assertNotNull(newUUID);
		assertEquals(newUUID, testBean.getId());
		
		SampleObjectBean newBean = repo.getSampleObject(newUUID);
		assertEquals(testBean, newBean);
	}

}
