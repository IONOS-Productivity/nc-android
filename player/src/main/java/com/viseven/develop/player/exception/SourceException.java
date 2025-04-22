package com.viseven.develop.player.exception;

/**
 * User: shevchuk anton
 * Date: 01/26/17
 */
public class SourceException extends Exception {
	public final int errorCode;

	public SourceException() {
		this(0);
	}

	public SourceException(int errorCode) {
		super("Source not found");
		this.errorCode = errorCode;
	}
}
