package com.strato.hidrive.domain.exception;

import java.io.IOException;

/**
 * User: Dima Muravyov
 * Date: 31.10.2016
 */

public class NotEnoughSpaceException extends IOException {
	public final String failedFileName;

	public NotEnoughSpaceException(String failedFileName) {
		this.failedFileName = failedFileName;
	}
}
