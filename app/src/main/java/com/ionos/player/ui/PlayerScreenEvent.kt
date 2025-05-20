package com.ionos.player.ui

import com.owncloud.android.datamodel.OCFile

sealed interface PlayerScreenEvent {
    data class ShowFileActions(val file: OCFile) : PlayerScreenEvent
}
