package com.ionos.domain.exception;

import java.io.IOException;

/**
 * Created by: Alex Kucherenko
 * Date: 30.03.2018.
 */

public class NoFreeLocalSpaceException extends IOException {
	public NoFreeLocalSpaceException() {
		this("NoFreeSpaceException");
	}

	public NoFreeLocalSpaceException(String message) {
		super(message);
	}
}
