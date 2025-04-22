package com.viseven.develop.media3

import androidx.media3.session.MediaSession
import com.viseven.develop.media3.session.MediaSessionActivityFactory
import kotlin.reflect.KProperty

interface PlaybackServiceComponent {

	fun provideMediaSession(): MediaSession

	fun provideMediaSessionActivityFactory(): MediaSessionActivityFactory

	interface Factory {

		fun create(): PlaybackServiceComponent
	}

	object FactoryProvider {
		private lateinit var factory: Factory

		fun initialize(factory: Factory) {
			this.factory = factory
		}

		operator fun getValue(thisRef: Any, property: KProperty<*>): Factory {
			return factory
		}
	}
}
