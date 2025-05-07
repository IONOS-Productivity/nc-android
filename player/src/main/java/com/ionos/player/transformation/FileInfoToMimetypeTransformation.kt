package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo

interface FileInfoToMimetypeTransformation: PlayerTransformation<PlayerFileInfo, String?> {
}