package com.hibu.etcd;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;

import io.dropwizard.Configuration;

public class EtcdConfiguration extends Configuration {
	private static final Logger logger = (Logger) Logger.getInstance(EtcdConfiguration.class);

	private static final String ETCD_SERVICE_URL = "http://10.1.42.1:4001/"; // Where the service is exposed to docker containers
	
	private final String basePath;
	private Map<String,String> cachedValues = new ConcurrentHashMap<>();
	
	private EtcdClient client = new EtcdClient(URI.create(ETCD_SERVICE_URL));
	
	public EtcdConfiguration(String basePath) {
		if (!basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		this.basePath = basePath;
	}
	
	protected String getSetting(String key) {
		if (cachedValues.containsKey(key)) {
			return cachedValues.get(key);
		}
		
		EtcdResult result;
		try {
			result = client.get(basePath + key);
		} catch (EtcdClientException e) {
			result = null;
		}
		
		if (result != null && !result.isError()) {
			cachedValues.put(key, result.node.value);
			addWatchSettingForChange(key, result);
			
			return result.node.value;
		} else {
			// Don't cache an error condition
			return null;
		}
	}
		
	private void addWatchSettingForChange(final String key, final EtcdResult result) {
		try {
			ListenableFuture<EtcdResult> watchFuture = this.client.watch(basePath + key, result.node.modifiedIndex + 1, true);
			Futures.addCallback(watchFuture, new FutureCallback<EtcdResult>() {
				@Override
				public void onSuccess(EtcdResult result) {
					if (result != null && !result.isError()) {
						cachedValues.put(key, result.node.value);
						addWatchSettingForChange(key, result);
					}
				}

				@Override
				public void onFailure(Throwable t) {
					addWatchSettingForChange(key, result);
				}				
			});
		} catch (EtcdClientException e) {
			logger.error(e);
		}	
	}
	
}
