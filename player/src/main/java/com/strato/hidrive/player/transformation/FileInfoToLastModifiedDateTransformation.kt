package com.strato.hidrive.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo

fun interface FileInfoToLastModifiedDateTransformation : PlayerTransformation<PlayerFileInfo, String>