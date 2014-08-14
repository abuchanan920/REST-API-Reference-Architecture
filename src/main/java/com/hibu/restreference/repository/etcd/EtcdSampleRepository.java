package com.hibu.restreference.repository.etcd;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibu.restreference.core.SampleObjectBean;
import com.hibu.restreference.repository.SampleRepository;
import com.hibu.restreference.repository.SampleRepositoryException;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdNode;
import com.justinsb.etcd.EtcdResult;

// Don't do this in a production system. Etcd isn't meant for this purpose. 
// I'm just using it for demo purposes to avoid adding another service to the mix.
public class EtcdSampleRepository implements SampleRepository {	
	private static final String CONFCHANNEL_ENV = "CONFCHANNEL";
	private static final String ETCD_SERVICE_URL = "http://10.1.42.1:4001/"; // Where the service is exposed to docker containers
	
	private final String basePath;
	private EtcdClient client = new EtcdClient(URI.create(ETCD_SERVICE_URL));
	private ObjectMapper mapper = new ObjectMapper();
	private int addSampleCount = 0;
	
	public EtcdSampleRepository() {
		this("/data/restreference");
	}

	public EtcdSampleRepository(String basePath) {
		if (!basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		if (System.getenv(CONFCHANNEL_ENV) != null) {
			basePath = System.getenv(CONFCHANNEL_ENV) + basePath;
		}

		this.basePath = basePath;
	}
	
	@Override
	public UUID addSampleObject(SampleObjectBean sampleObject)
			throws SampleRepositoryException {
		addSampleCount++;
		sampleObject.setId(UUID.randomUUID());
		updateSampleObject(sampleObject);
		return sampleObject.getId();
	}

	@Override
	public List<SampleObjectBean> getAllSampleObjects()
			throws SampleRepositoryException {
		try {
			List<EtcdNode> list = client.listChildren(basePath).node.nodes;
			List<SampleObjectBean> result = new ArrayList<>(list.size());
			for (EtcdNode item : list) {
				result.add(mapper.readValue(item.value, SampleObjectBean.class));
			}
			return result;
		} catch (IOException e) {
			throw new SampleRepositoryException(e);
		}
	}

	@Override
	public SampleObjectBean getSampleObject(UUID id)
			throws SampleRepositoryException {
		try {
			EtcdResult result = client.get(basePath + id);
			return mapper.readValue(result.node.value, SampleObjectBean.class);
		} catch (IOException e) {
			throw new SampleRepositoryException(e);
		}
	}

	@Override
	public int getSampleObjectCount() throws SampleRepositoryException {
		try {
			return client.listChildren(basePath).node.nodes.size();
		} catch (EtcdClientException e) {
			throw new SampleRepositoryException(e);
		}
	}

	@Override
	public void updateSampleObject(SampleObjectBean sampleObject)
			throws SampleRepositoryException {
		try {
			String json = mapper.writeValueAsString(sampleObject);
			client.set(basePath + sampleObject.getId(), json);
		} catch (EtcdClientException | JsonProcessingException e) {
			throw new SampleRepositoryException(e);
		}
	}

	@Override
	public void deleteSampleObject(UUID id) throws SampleRepositoryException {
		try {
			client.delete(basePath + id);
		} catch (EtcdClientException e) {
			throw new SampleRepositoryException(e);
		}
	}

	@Override
	public int getAddSampleCount() {
		return addSampleCount;
	}

}
