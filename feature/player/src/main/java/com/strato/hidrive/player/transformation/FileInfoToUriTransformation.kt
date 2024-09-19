package com.strato.hidrive.player.transformation

import android.net.Uri
import com.strato.hidrive.player.domain.PlayerFileInfo


interface FileInfoToUriTransformation : PlayerTransformation<PlayerFileInfo, Uri>