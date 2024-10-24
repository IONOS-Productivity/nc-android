package com.ionos.scanbot.exception

import kotlin.reflect.KClass

internal class CreateIntentException(
	targetClass: KClass<*>,
) : Exception("${targetClass.java.simpleName} should be created via createIntent")
