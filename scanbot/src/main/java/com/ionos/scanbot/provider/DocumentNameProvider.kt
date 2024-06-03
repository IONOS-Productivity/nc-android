package com.ionos.scanbot.provider

import android.content.Context
import com.ionos.scanbot.R
import java.util.Calendar
import javax.inject.Inject

/**
 * User: Dima Muravyov
 * Date: 08.12.2017
 */
internal class DocumentNameProvider(
	private val calendar: Calendar,
	private val formattedName: String,
) {

	@Inject
	constructor(context: Context) : this(
		Calendar.getInstance(),
		context.getString(R.string.scanbot_document_name_format),
	)

	fun getName(): String {
		return String.format(formattedName, calendar)
	}
}