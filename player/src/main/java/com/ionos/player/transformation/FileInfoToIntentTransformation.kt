package com.ionos.player.transformation

import android.content.Intent
import com.ionos.player.model.PlayerFileInfo

fun interface FileInfoToIntentTransformation :
	PlayerTransformation<PlayerFileInfo, Intent?>