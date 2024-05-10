package com.strato.hidrive.domain.interfaces.actions.null_objects;

import com.strato.hidrive.domain.interfaces.actions.ParamAction;

/**
 * User: zuzik
 * Date: 6/16/16
 */
public class NullParamAction<T> implements ParamAction<T> {
	@Override
	public void execute(T value) {
	}
}
