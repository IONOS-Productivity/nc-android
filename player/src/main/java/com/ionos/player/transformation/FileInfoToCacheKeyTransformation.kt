package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo

interface FileInfoToCacheKeyTransformation : PlayerTransformation<PlayerFileInfo, String>