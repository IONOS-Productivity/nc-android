package com.ionos.player.predicate

interface PlayerPredicate<T> {
	fun satisfied(value: T): Boolean
}