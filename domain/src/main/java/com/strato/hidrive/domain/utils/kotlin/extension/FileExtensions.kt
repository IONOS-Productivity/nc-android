package com.strato.hidrive.domain.utils.kotlin.extension

import java.io.File

fun File.deleteIfExists() {
	if (exists()) {
		delete()
	}
}