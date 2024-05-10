package com.strato.hidrive.domain.predicate;

import java.util.Arrays;
import java.util.List;

public class CompositePredicate<T> implements Predicate<T> {
	private final List<Predicate<T>> predicates;

	@SafeVarargs
	public CompositePredicate(Predicate<T>... predicates) {
		this.predicates = Arrays.asList(predicates);
	}

	@Override
	public boolean satisfied(T value) {
		for (Predicate<T> predicate : this.predicates) {
			if (!predicate.satisfied(value)) {
				return false;
			}
		}
		return true;
	}
}
