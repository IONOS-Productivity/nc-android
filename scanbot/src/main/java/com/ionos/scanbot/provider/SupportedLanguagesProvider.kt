package com.ionos.scanbot.provider

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import io.scanbot.sdk.entity.Language
import java.util.Locale
import javax.inject.Inject

internal class SupportedLanguagesProvider @Inject constructor(
	private val context: Context,
) {

	fun get(): Set<Language> = mutableSetOf<Language>().apply {
		add(Language.DEU)
		add(Language.ENG)
		getLocalLanguage()?.let { add(it) }
	}

	private fun getLocalLanguage(): Language? = when (getLocalLanguageCode()) {
		"fr" -> Language.FRA
		"nl" -> Language.NLD
		"pt" -> Language.POR
		"es" -> Language.SPA
		"tr" -> Language.TUR
		else -> null
	}

	private fun getLocalLanguageCode(): String = context
		.resources
		.configuration
		.getPrimaryLocale()
		.language

	private fun Configuration.getPrimaryLocale(): Locale = when {
		Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> locales[0]
		else -> locale
	}
}
