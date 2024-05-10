package com.strato.hidrive.api.bll

enum class OnExist(val behavior: String) {
	AUTONAME("autoname"),
	OVERWRITE("overwrite")
}