package com.strato.hidrive.player.transformation

interface PlayerTransformation<From, To> {
	fun transform(from: From): To
}