package com.strato.hidrive.domain.exception;

public class UploadCanceledException extends Exception {
	public UploadCanceledException() {
		this("Upload canceled");
	}

	public UploadCanceledException(String message) {
		super(message);
	}
}
