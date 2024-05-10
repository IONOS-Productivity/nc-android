package com.strato.hidrive.scanbot.di.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.*

/**
 * User: Dima Muravyov
 * Date: 19.03.2018
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(FIELD, FUNCTION, PROPERTY, VALUE_PARAMETER)
annotation class Scanbot