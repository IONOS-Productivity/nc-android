package com.strato.hidrive.player.chromecast

data class PlayerModelState(
	val receiverState: PlayerChromecastModel.ReceiverState,
){
	val connected = receiverState == PlayerChromecastModel.ReceiverState.CONNECTED
	val availableDevicesPresent =
		receiverState != PlayerChromecastModel.ReceiverState.NO_DEVICES_AVAILABLE
}
