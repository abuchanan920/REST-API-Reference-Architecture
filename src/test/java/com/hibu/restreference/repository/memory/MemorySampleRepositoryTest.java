package com.hibu.restreference.repository.memory;

import com.hibu.restreference.repository.SampleRepositoryTest;

public class MemorySampleRepositoryTest extends SampleRepositoryTest {

	public MemorySampleRepositoryTest() {
		repo = new MemorySampleRepository();
	}

}
