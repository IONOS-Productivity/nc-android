package com.strato.hidrive.player.transformation

import android.content.Intent
import com.strato.hidrive.player.domain.PlayerFileInfo

fun interface FileInfoToIntentTransformation :
	PlayerTransformation<PlayerFileInfo, Intent?>