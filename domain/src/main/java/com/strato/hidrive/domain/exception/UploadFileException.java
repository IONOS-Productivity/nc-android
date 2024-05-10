package com.strato.hidrive.domain.exception;

/**
 * Created by: Alex Kucherenko
 * Date: 30.03.2018.
 */

public class UploadFileException extends Exception {
	public UploadFileException() {
		this("UploadFileException");
	}

	public UploadFileException(String message) {
		super(message);
	}
}
