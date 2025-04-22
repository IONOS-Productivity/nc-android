package com.ionos.player.media3.session

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.ionos.player.player_mode.PlayerMode
import dagger.assisted.AssistedFactory

@AssistedFactory
@OptIn(UnstableApi::class)
interface SwitchToModeListenerFactory {
    fun create(session: MediaSession, initialMode: PlayerMode.Mode): SwitchToModeListener
}
