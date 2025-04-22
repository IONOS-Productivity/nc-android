package com.ionos.player.transformation

interface PlayerTransformation<From, To> {
	fun transform(from: From): To
}