package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo

fun interface FileInfoToDisplayNameTransformation :
	PlayerTransformation<PlayerFileInfo, String>