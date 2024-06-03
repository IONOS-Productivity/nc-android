package com.ionos.domain.optional;

public interface Function<T, R> {
	R apply(T arg);
}