package com.strato.hidrive.domain.predicate;

/**
 * Created by Anton Shevchuk on 12.08.2016.
 */

public interface Predicate <T> {
	boolean satisfied(T value);
}
