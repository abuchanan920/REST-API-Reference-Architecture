package com.hibu.restreference.repository;

/**
 * Wraps any exceptions thrown by SampleRepository implementations allowing the client code to handle them all in a consistent manner.
 * 
 * @author andrew.buchanan@hibu.com
 *
 */
public class SampleRepositoryException extends Exception {
	
	private static final long serialVersionUID = 5427800629889466064L;
	
	public SampleRepositoryException(String msg) {
		super(msg);
	}

}
