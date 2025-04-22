package com.ionos.player.media3.session

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.ionos.player.chromecast.PlayerChromecastModel
import java.util.Optional
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.optionals.getOrNull

@Singleton
@OptIn(UnstableApi::class)
class HiDriveMediaSessionHolder @Inject constructor(
	private val chromecastModel: Optional<PlayerChromecastModel>,
	private val switchToModeListenerFactory: SwitchToModeListenerFactory,
) : MediaSessionHolder {
	private var session: MediaSession? = null
	private var switchToModeListener: SwitchToModeListener? = null

	override fun init(session: MediaSession) {
		this.session = session
        chromecastModel.ifPresent {
            this.switchToModeListener = switchToModeListenerFactory
                .create(session, it.state().getPlayerMode())
                .also(it::addListener)
        }
	}

	override fun get(): MediaSession? {
		return session
	}

	override fun release() {
		switchToModeListener?.let {
			this.switchToModeListener = null
			chromecastModel.getOrNull()?.removeListener(it)
		}
		session?.let {
			this.session = null
			it.player.release()
			it.release()
		}
	}
}
