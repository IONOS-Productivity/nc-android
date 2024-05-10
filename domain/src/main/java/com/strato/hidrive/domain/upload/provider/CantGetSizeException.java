package com.strato.hidrive.domain.upload.provider;

/**
 * Created by Anton Shevchuk on 14.11.2016.
 */

public class CantGetSizeException extends Exception {
	public CantGetSizeException() {
		super(CantGetSizeException.class.getName());
	}
}
