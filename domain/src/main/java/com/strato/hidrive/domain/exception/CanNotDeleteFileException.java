package com.strato.hidrive.domain.exception;

public class CanNotDeleteFileException extends Exception {
	public CanNotDeleteFileException() {
		this("Can't delete file");
	}

	public CanNotDeleteFileException(String message) {
		super(message);
	}
}
