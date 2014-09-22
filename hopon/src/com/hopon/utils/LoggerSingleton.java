package com.hopon.utils;

import org.apache.log4j.Logger;

public class LoggerSingleton {
	private static LoggerSingleton instance = null;
	private final static Logger LOG = Logger.getLogger(LoggerSingleton.class.getName());
	private LoggerSingleton() { } // just to make sure that from no where object of this class can be created.
	public static LoggerSingleton getInstance() {
		if(instance == null) {
			instance = new LoggerSingleton();
		}
		return instance;
	}
	public void debug(String message){
		LoggerSingleton.LOG.debug(message);
	}
	public void info(String message){
		LoggerSingleton.LOG.info(message);
	}
	public void warn(String message){
		LoggerSingleton.LOG.warn(message);
	}
	public void fatal(String message){
		LoggerSingleton.LOG.fatal(message);
	}
	public void error(String message){
		LoggerSingleton.LOG.error(message);
	}
}
