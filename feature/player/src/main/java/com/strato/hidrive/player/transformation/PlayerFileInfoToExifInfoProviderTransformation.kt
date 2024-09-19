package com.strato.hidrive.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.views.exif_info.ExifInfoProvider


fun interface PlayerFileInfoToExifInfoProviderTransformation :
	PlayerTransformation<PlayerFileInfo, ExifInfoProvider?>