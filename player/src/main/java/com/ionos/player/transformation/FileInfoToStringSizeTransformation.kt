package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo

fun interface FileInfoToStringSizeTransformation : PlayerTransformation<PlayerFileInfo, String>