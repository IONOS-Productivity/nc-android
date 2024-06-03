package com.ionos.domain.interfaces.actions.null_objects;

import com.ionos.domain.interfaces.actions.ParamAction;

/**
 * User: zuzik
 * Date: 6/16/16
 */
public class NullParamAction<T> implements ParamAction<T> {
	@Override
	public void execute(T value) {
	}
}
