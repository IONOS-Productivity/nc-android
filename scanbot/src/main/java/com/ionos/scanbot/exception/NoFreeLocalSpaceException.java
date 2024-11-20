package com.ionos.scanbot.exception;

import java.io.IOException;


public class NoFreeLocalSpaceException extends IOException {
	public NoFreeLocalSpaceException() {
		this("NoFreeSpaceException");
	}

	public NoFreeLocalSpaceException(String message) {
		super(message);
	}
}
