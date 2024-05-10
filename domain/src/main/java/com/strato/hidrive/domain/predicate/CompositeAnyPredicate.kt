package com.strato.hidrive.domain.predicate

class CompositeAnyPredicate<T>(
	vararg predicates: Predicate<T>,
) : Predicate<T> {
	private val predicates = predicates.toList()

	override fun satisfied(value: T): Boolean {
		return predicates
			.any { it.satisfied(value) }
	}

}