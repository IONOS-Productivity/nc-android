package com.strato.hidrive.api.connection.gateway.exceptions;

/**
 * Created by: Alex Kucherenko
 * Date: 30.03.2018.
 */

public class UnexpectedErrorException extends Exception {
	public UnexpectedErrorException() {
		this("UnexpectedErrorException");
	}

	public UnexpectedErrorException(String message) {
		super(message);
	}
}
