package com.ionos.common_ui.dialog.optional;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dima Muravyov on 19.09.2018.
 */
@FunctionalInterface
public interface Consumer<T> {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument
	 */
	void accept(@NotNull T t);
}
