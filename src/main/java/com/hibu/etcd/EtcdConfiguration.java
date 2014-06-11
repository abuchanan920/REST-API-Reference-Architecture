package com.hibu.etcd;

import java.net.URI;

import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;

import io.dropwizard.Configuration;

public class EtcdConfiguration extends Configuration {
	private static final String ETCD_SERVICE_URL = "http://10.1.42.1:4001/"; // Where the service is exposed to docker containers
	
	private final String basePath;
	
	private EtcdClient client = new EtcdClient(URI.create(ETCD_SERVICE_URL));
	
	public EtcdConfiguration(String basePath) {
		if (!basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		this.basePath = basePath;
	}
	
	protected String getSetting(String key) {
		EtcdResult result;
		try {
			result = client.get(basePath + key);
			if (!result.isError()) {
				return result.value;
			} else {
				return null;
			}
		} catch (EtcdClientException e) {
			return null;
		}
	}
	
}
