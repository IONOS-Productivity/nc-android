package com.ionos.scanbot.util.io

import java.io.File

internal fun File.removeRecursive(): Boolean {
	if (exists() && isDirectory) {
		val children = list() ?: return delete()
		for (child in children) {
			val success = File(this, child).removeRecursive()
			if (!success) {
				return false
			}
		}
	}
	return delete()
}
