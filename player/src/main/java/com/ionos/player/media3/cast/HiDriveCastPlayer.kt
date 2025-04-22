package com.ionos.player.media3.cast

import androidx.media3.cast.CastPlayer
import androidx.media3.cast.MediaItemConverter
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.google.android.gms.cast.MediaError
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.error.ChromecastMediaError
import com.ionos.player.error.ChromecastUnsupportedFileException
import com.ionos.player.predicate.AllowedChromecastMediaFormatPredicate
import com.ionos.player.media3.store.SourceInfoStore

@UnstableApi
class HiDriveCastPlayer(
	private val castContext: CastContext,
	private val mediaItemConverter: MediaItemConverter,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val allowedChromecastMediaFormatPredicate: AllowedChromecastMediaFormatPredicate,
	private val delegate: CastPlayer = CastPlayer(castContext, mediaItemConverter),
) : Player by delegate {

	private val listeners: MutableSet<Player.Listener> = mutableSetOf()
	private val castSessionManagerListener = CastSessionManagerListener()
	private val remoteMediaClientCallback = RemoteMediaClientCallback()
	private var remoteMediaClient: RemoteMediaClient? = null

	init {
		addListener(InternalListener())
		castContext.sessionManager.run {
			currentCastSession?.remoteMediaClient?.let(::setRemoteMediaClient)
			addSessionManagerListener(castSessionManagerListener, CastSession::class.java)
		}
	}

	override fun addListener(listener: Player.Listener) {
		delegate.addListener(listener)
		listeners.add(listener)
	}

	override fun removeListener(listener: Player.Listener) {
		delegate.removeListener(listener)
		listeners.remove(listener)
	}

	override fun release() {
		delegate.release()
		listeners.clear()
		castContext.sessionManager.removeSessionManagerListener(
			castSessionManagerListener,
			CastSession::class.java,
		)
		remoteMediaClient?.unregisterCallback(remoteMediaClientCallback)
	}

	private fun setRemoteMediaClient(remoteMediaClient: RemoteMediaClient?) {
		this.remoteMediaClient?.unregisterCallback(remoteMediaClientCallback)
		remoteMediaClient?.registerCallback(remoteMediaClientCallback)
		this.remoteMediaClient = remoteMediaClient
	}

	private inner class InternalListener : Player.Listener {

		override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
			val sourceInfo = mediaItem?.mediaId?.let(sourceInfoStore::getSourceInfo)
			if (sourceInfo != null && !allowedChromecastMediaFormatPredicate.satisfied(sourceInfo)) {
				val chromecastUnsupportedFileException = ChromecastUnsupportedFileException()
				val playbackException = PlaybackException(
					chromecastUnsupportedFileException.message,
					chromecastUnsupportedFileException,
					PlaybackException.ERROR_CODE_REMOTE_ERROR,
				)
				listeners.forEach { it.onPlayerError(playbackException) }
				pause()
			}
		}
	}

	private inner class RemoteMediaClientCallback : RemoteMediaClient.Callback() {

		override fun onMediaError(mediaError: MediaError) {
			val playbackException = PlaybackException(
				mediaError.reason,
                ChromecastMediaError(mediaError),
				PlaybackException.ERROR_CODE_REMOTE_ERROR,
			)
			listeners.forEach { it.onPlayerError(playbackException) }
		}
	}

	private inner class CastSessionManagerListener : SessionManagerListener<CastSession> {

		override fun onSessionStarted(castSession: CastSession, p1: String) {
			setRemoteMediaClient(castSession.remoteMediaClient)
		}

		override fun onSessionResumed(castSession: CastSession, p1: Boolean) {
			setRemoteMediaClient(castSession.remoteMediaClient)
		}

		override fun onSessionSuspended(castSession: CastSession, p1: Int) {
			setRemoteMediaClient(castSession.remoteMediaClient)
		}

		override fun onSessionEnded(castSession: CastSession, p1: Int) {
			setRemoteMediaClient(castSession.remoteMediaClient)
		}

		override fun onSessionStarting(castSession: CastSession) {}

		override fun onSessionStartFailed(castSession: CastSession, p1: Int) {}

		override fun onSessionResuming(castSession: CastSession, p1: String) {}

		override fun onSessionResumeFailed(castSession: CastSession, p1: Int) {}

		override fun onSessionEnding(p0: CastSession) {}
	}
}
