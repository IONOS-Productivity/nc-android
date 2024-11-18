package com.ionos.scanbot.provider

import io.scanbot.sdk.entity.Language
import javax.inject.Inject

internal class SupportedLanguagesProvider @Inject constructor(
) {

    fun get(): Set<Language> = setOf(
        Language.FRA,
        Language.ENG,
    )

}
