package com.ionos.player.chromecast

interface PlayerChromecastModel {

	enum class ReceiverState {
		NO_DEVICES_AVAILABLE,
		NOT_CONNECTED,
		CONNECTING,
		CONNECTED
	}

	fun state(): PlayerModelState

	fun addListener(listener: Listener)

	fun removeListener(listener: Listener)

	fun getCastDeviceName(): String

	fun addReceiverStateChangeListener(receiverStateChangeListener: ReceiverStateChangeListener)

	fun removeReceiverStateChangeListener(receiverStateChangeListener: ReceiverStateChangeListener)


	interface Listener {
		fun onUpdate(state: PlayerModelState)

		fun onApplicationConnectedToCastSession()
	}

	interface ReceiverStateChangeListener {
		fun onStateChange()
	}
}