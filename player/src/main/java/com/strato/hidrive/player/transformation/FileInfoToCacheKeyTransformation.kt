package com.strato.hidrive.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo

interface FileInfoToCacheKeyTransformation : PlayerTransformation<PlayerFileInfo, String>