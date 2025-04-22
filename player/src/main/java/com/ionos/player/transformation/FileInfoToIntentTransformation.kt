package com.ionos.player.transformation

import android.content.Intent
import com.ionos.player.domain.PlayerFileInfo

fun interface FileInfoToIntentTransformation :
	PlayerTransformation<PlayerFileInfo, Intent?>