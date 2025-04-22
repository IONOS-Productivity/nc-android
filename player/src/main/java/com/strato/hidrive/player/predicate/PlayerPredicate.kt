package com.strato.hidrive.player.predicate

interface PlayerPredicate<T> {
	fun satisfied(value: T): Boolean
}