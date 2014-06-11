package com.hibu.etcd;

import io.dropwizard.Configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ConfigurationServlet implements Servlet {
	private final Configuration config;
	private static final Set<String> ignoredMethods = new HashSet<>();
	
	static {
		ignoredMethods.add("getLoggingFactory");
		ignoredMethods.add("getServerFactory");
		ignoredMethods.add("getMetricsFactory");
		ignoredMethods.add("getClass");
	}
	
	public ConfigurationServlet(Configuration configuration) {
		this.config = configuration;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// No-op
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		
		for (Method m : config.getClass().getMethods())
		    if (m.getName().startsWith("get") 
		    		&& m.getParameterTypes().length == 0
		    		&& !ignoredMethods.contains(m.getName())) {
		    	try {
		    		String r = (String)m.invoke(config);
		    		writer.write(m.getName().substring(3) + " : " + r + "\n");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		    }
		
		writer.flush();
		writer.close();	
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
		// No-op
	}

}
