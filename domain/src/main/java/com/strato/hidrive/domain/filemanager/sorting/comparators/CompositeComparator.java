package com.strato.hidrive.domain.filemanager.sorting.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * User: Dima Muravyov
 * Date: 03.08.2016
 */
public class CompositeComparator<T> implements Comparator<T> {

	private final List<Comparator<T>> comparators;

	@SafeVarargs
	public CompositeComparator(Comparator<T>... comparators) {
		this.comparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(T leftHandSize, T rightHandSize) {
		for (Comparator<T> comparator : this.comparators) {
			int result = comparator.compare(leftHandSize, rightHandSize);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
