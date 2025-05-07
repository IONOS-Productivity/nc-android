package com.ionos.player.model.predicate

interface PlayerPredicate<T> {
	fun satisfied(value: T): Boolean
}