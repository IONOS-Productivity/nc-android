package com.strato.hidrive.domain.exception;

/**
 * Created by: Alex Kucherenko
 * Date: 30.03.2018.
 */

public class SynchronizationErrorException extends Exception {
	public SynchronizationErrorException() {
		this("SynchronizationErrorException");
	}

	public SynchronizationErrorException(String message) {
		super(message);
	}
}
