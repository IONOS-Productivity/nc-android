package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo

fun interface FileInfoToLastModifiedDateTransformation : PlayerTransformation<PlayerFileInfo, String>