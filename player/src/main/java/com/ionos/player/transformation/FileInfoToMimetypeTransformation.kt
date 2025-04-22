package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo

interface FileInfoToMimetypeTransformation: PlayerTransformation<PlayerFileInfo, String?> {
}