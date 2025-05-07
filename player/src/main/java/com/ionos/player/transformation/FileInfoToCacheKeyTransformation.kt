package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo

interface FileInfoToCacheKeyTransformation : PlayerTransformation<PlayerFileInfo, String>