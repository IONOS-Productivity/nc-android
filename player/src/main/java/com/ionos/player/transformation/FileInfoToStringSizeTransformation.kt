package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo

fun interface FileInfoToStringSizeTransformation : PlayerTransformation<PlayerFileInfo, String>