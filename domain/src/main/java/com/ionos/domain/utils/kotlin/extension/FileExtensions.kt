package com.ionos.domain.utils.kotlin.extension

import java.io.File

fun File.deleteIfExists() {
	if (exists()) {
		delete()
	}
}