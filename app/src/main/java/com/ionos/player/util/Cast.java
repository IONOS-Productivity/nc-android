package com.ionos.player.util;

public class Cast {

	public static <T> T castOrError(Object objectToCast, Class<T> implementationInterface) throws ClassCastException {
		return implementationInterface.cast(objectToCast);
	}
}
