package com.strato.hidrive.domain.exception;

/**
 * User: zuzik
 * Date: 25.03.2016.
 */
public class InterfaceNotImplementedException extends ClassCastException {
	public InterfaceNotImplementedException(Object objectWithoutInterface, Class interfaceForImplementation) {
		super(String.format("%s interface is not implemented in %s object",
				interfaceForImplementation.getSimpleName(),
				objectWithoutInterface.getClass().getSimpleName()));
	}
}
