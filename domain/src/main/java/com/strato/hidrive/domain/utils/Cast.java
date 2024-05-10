package com.strato.hidrive.domain.utils;

import androidx.annotation.Nullable;

import com.strato.hidrive.domain.exception.InterfaceNotImplementedException;
import com.strato.hidrive.domain.interfaces.actions.ParamAction;
import com.strato.hidrive.domain.logger.LoggerUtil;

/**
 * Created by yaz on 7/29/16.
 */
public class Cast {

	public static <T> T castOrDefault(@Nullable Object objectToCast, Class<T> implementationInterface, T defaultValue) {
		if (objectToCast != null) {
			try {
				return implementationInterface.cast(objectToCast);
			} catch (ClassCastException e) {
				LoggerUtil.logD(Cast.class.getSimpleName(), "Can't cast " + objectToCast + " to " + implementationInterface);
			}
		}
		return defaultValue;
	}

	public static <T> void cast(@Nullable Object objectToCast, Class<T> implementationInterface, ParamAction<T> success) {
		if (objectToCast == null) {
			return;
		}
		try {
			T castResult = implementationInterface.cast(objectToCast);
			success.execute(castResult);
		} catch (ClassCastException e) {
			LoggerUtil.logD(Cast.class.getSimpleName(), "Can't cast " + objectToCast + " to " + implementationInterface);
		}
	}

	public static <T> T castOrError(Object objectToCast, Class<T> implementationInterface) throws InterfaceNotImplementedException {
		try {
			return implementationInterface.cast(objectToCast);
		} catch (ClassCastException e) {
			throw new InterfaceNotImplementedException(objectToCast, implementationInterface);
		}
	}
}
