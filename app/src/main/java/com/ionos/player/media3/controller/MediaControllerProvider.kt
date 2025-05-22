package com.ionos.player.media3.controller

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlin.reflect.KProperty

class MediaControllerProvider(
	private val controllerFactory: MediaControllerFactory
) {
	private var controllerFuture: ListenableFuture<MediaController>? = null
	private val initializeListeners = mutableListOf<(Result<MediaController>) -> Unit>()

	private val controller: MediaController?
		get() = controllerFuture
			?.takeIf { it.isDone }
			?.let { runCatching { it.get() } }
			?.getOrNull()
			?.takeIf { it.isConnected }

	val isInitialized: Boolean
		get() = controller != null

	val isInitializing: Boolean
		get() = controllerFuture.let { it != null && !it.isDone }

	fun addInitializeListener(initializeListener: (Result<MediaController>) -> Unit) {
		initializeListeners.add(initializeListener)
	}

	fun initialize(context: Context, initializeListener: (Result<MediaController>) -> Unit) {
		initializeListeners.add(initializeListener)

		val controllerFuture = controllerFactory.create(context)
		this.controllerFuture = controllerFuture

		Futures.addCallback(controllerFuture, object : FutureCallback<MediaController> {

			override fun onSuccess(controller: MediaController) {
				initializeListeners.forEach { it.invoke(Result.success(controller)) }
				initializeListeners.clear()
			}

			override fun onFailure(throwable: Throwable) {
				initializeListeners.forEach { it.invoke(Result.failure(throwable)) }
				initializeListeners.clear()
			}

		}, ContextCompat.getMainExecutor(context))
	}

	fun release() {
		controllerFuture?.let(MediaController::releaseFuture)
		controllerFuture = null
	}

	fun get(): MediaController? {
		return controller
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>): MediaController? {
		return controller
	}
}
