package com.hopon.utils;

@SuppressWarnings("serial")
public class ControllerException extends Exception {

	public ControllerException() {
		super();
	}

	public ControllerException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ControllerException(final String arg0) {
		super(arg0);
	}

	public ControllerException(final Throwable arg0) {
		super(arg0);
	}

}
