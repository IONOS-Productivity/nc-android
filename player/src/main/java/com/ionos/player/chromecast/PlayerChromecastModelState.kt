package com.ionos.player.chromecast

import com.ionos.player.player_mode.PlayerMode

data class PlayerChromecastModelState(
	val receiverState: PlayerChromecastModel.ReceiverState,
){
	val connected = receiverState == PlayerChromecastModel.ReceiverState.CONNECTED
	val availableDevicesPresent =
		receiverState != PlayerChromecastModel.ReceiverState.NO_DEVICES_AVAILABLE

	fun getPlayerMode(): PlayerMode.Mode {
		return if (receiverState == PlayerChromecastModel.ReceiverState.CONNECTED) {
			PlayerMode.Mode.CHROMECAST
		} else {
			PlayerMode.Mode.REGULAR
		}
	}
}
