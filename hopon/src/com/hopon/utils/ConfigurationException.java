package com.hopon.utils;

@SuppressWarnings("serial")
public class ConfigurationException extends Exception {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ConfigurationException(final String arg0) {
		super(arg0);
	}

	public ConfigurationException(final Throwable arg0) {
		super(arg0);
	}

}
