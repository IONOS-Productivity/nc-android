package com.strato.hidrive.domain.exception;

public class FileIsNotFullyCachedException extends Exception {
	public FileIsNotFullyCachedException() {
		this("FileIsNotFullyCachedException");
	}

	public FileIsNotFullyCachedException(String message) {
		super(message);
	}
}
