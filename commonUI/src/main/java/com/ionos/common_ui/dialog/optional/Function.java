package com.ionos.common_ui.dialog.optional;

public interface Function<T, R> {
	R apply(T arg);
}