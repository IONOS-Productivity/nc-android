package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo

fun interface FileInfoToDisplayNameTransformation :
	PlayerTransformation<PlayerFileInfo, String>