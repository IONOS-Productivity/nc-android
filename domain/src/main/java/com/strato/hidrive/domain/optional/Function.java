package com.strato.hidrive.domain.optional;

public interface Function<T, R> {
	R apply(T arg);
}