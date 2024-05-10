package com.strato.hidrive.scanbot.di.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.*

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(FIELD, FUNCTION, PROPERTY, VALUE_PARAMETER)
annotation class ScanbotLicenseKey