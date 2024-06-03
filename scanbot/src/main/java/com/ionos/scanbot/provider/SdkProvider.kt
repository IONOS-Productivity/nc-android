package com.ionos.scanbot.provider

import android.content.Context
import io.scanbot.sdk.ScanbotSDK
import javax.inject.Inject
import javax.inject.Singleton

//TODO alk - make the class internal

@Singleton
 class SdkProvider @Inject constructor(
	private val context: Context,
) {
	private val sdk by lazy { ScanbotSDK(context) }

	fun get() = sdk
}
