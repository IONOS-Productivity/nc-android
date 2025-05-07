package com.ionos.player.transformation

import android.net.Uri
import com.ionos.player.model.PlayerFileInfo

interface FileInfoToUriTransformation : PlayerTransformation<PlayerFileInfo, Uri>