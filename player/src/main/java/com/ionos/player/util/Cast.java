package com.ionos.player.util;

/**
 * Created by yaz on 7/29/16.
 */
public class Cast {

	public static <T> T castOrError(Object objectToCast, Class<T> implementationInterface) throws ClassCastException {
		return implementationInterface.cast(objectToCast);
	}
}
