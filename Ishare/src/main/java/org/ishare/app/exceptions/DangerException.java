package org.ishare.app.exceptions;

public class DangerException extends Exception {
	public DangerException() {
		super();
	}

	public DangerException(String mensaje) {
		super(mensaje);
	}
}