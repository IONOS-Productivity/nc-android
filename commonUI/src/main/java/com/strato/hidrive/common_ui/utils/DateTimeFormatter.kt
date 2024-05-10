package com.strato.hidrive.common_ui.utils

import java.util.Date

interface DateTimeFormatter {
	fun format(date: Date): String
}
