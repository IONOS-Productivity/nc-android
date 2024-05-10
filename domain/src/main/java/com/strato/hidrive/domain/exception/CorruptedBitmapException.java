package com.strato.hidrive.domain.exception;

/**
 * Created by: Alex Kucherenko
 * Date: 30.03.2018.
 */

public class CorruptedBitmapException extends Exception {
	public CorruptedBitmapException() {
		this("CorruptedBitmapException");
	}

	public CorruptedBitmapException(String message) {
		super(message);
	}
}
