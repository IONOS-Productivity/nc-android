/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.audio

import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.ThumbnailLoader
import com.ionos.player.model.state.PlaybackItemMetadata
import com.ionos.player.model.state.PlaybackState
import com.owncloud.android.R
import com.owncloud.android.databinding.PlayerAudioSourceFragmentBinding
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.Date
import javax.inject.Inject

class AudioPlayerSourceFragment : Fragment() {

    companion object {
        private const val ARGUMENT_FILE = "ARGUMENT_FILE"

        @JvmStatic
        fun createInstance(file: PlaybackFile): Fragment {
            val fragment = AudioPlayerSourceFragment()
            fragment.arguments = bundleOf(ARGUMENT_FILE to file)
            return fragment
        }
    }

    @Inject
    lateinit var playbackModel: PlaybackModel

    @Inject
    lateinit var thumbnailLoader: ThumbnailLoader

    private lateinit var binding: PlayerAudioSourceFragmentBinding
    private lateinit var file: PlaybackFile
    private lateinit var loadFileThumbnailJob: Deferred<Result<Unit>>
    private var metadata: PlaybackItemMetadata? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        file = arguments?.getSerializable(ARGUMENT_FILE) as PlaybackFile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PlayerAudioSourceFragmentBinding.inflate(inflater, container, false)
        binding.title.isSelected = true
        binding.title.text = file.getNameWithoutExtension()
        binding.fileDetails.text = file.getDetailsText()
        loadFileThumbnailJob = loadFileThumbnail()
        return binding.getRoot()
    }

    override fun onStart() {
        super.onStart()
        playbackModel.addListener(playbackModelListener)
    }

    override fun onStop() {
        playbackModel.removeListener(playbackModelListener)
        super.onStop()
    }

    private fun onPlaybackStateUpdate(state: PlaybackState) {
        state.currentItemState.ifPresent {
            if (it.file.id == file.id && it.metadata != null && it.metadata != metadata) {
                onMetadataUpdate(it.metadata)
            }
        }
    }

    private fun onMetadataUpdate(metadata: PlaybackItemMetadata) {
        this.metadata = metadata
        if (loadFileThumbnailJob.isCompleted && loadFileThumbnailJob.getCompleted().isFailure) {
            loadMetadataArtwork(metadata)
        }
        binding.title.text = if (metadata.artist.isNullOrEmpty()) {
            metadata.title
        } else {
            getString(R.string.player_audio_source_artist_and_title, metadata.artist, metadata.title)
        }
    }

    private fun loadFileThumbnail(): Deferred<Result<Unit>> {
        return viewLifecycleOwner.lifecycleScope.async {
            val thumbnailSize = resources.getDimension(R.dimen.player_full_screen_audio_player_album_cover_width)
            val thumbnail = thumbnailLoader.await(requireContext(), file, thumbnailSize.toInt(), thumbnailSize.toInt())
            if (thumbnail != null) {
                binding.albumCover.setImageBitmap(thumbnail)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Thumbnail not found"))
            }
        }
    }

    private fun loadMetadataArtwork(metadata: PlaybackItemMetadata) {
        val source = metadata.artworkData ?: metadata.artworkUri ?: return
        Glide.with(requireContext()).load(source).run {
            if (source is ByteArray) {
                diskCacheStrategy(DiskCacheStrategy.NONE)
                signature(StringSignature(file.id))
            }
            error(R.drawable.player_ic_album_cover_audio)
            into(binding.albumCover)
        }
    }

    private fun PlaybackFile.getDetailsText(): String {
        fun formatDate(timestamp: Long) = DateFormat.getDateFormat(context).format(Date(timestamp))
        val size = if (contentLength > 0) Formatter.formatFileSize(context, contentLength) else ""
        val date = if (lastModified > 0) getString(R.string.player_last_change_date, formatDate(lastModified)) else ""
        return if (size.isNotEmpty() && date.isNotEmpty()) "$size, $date" else size + date
    }

    private val playbackModelListener = object : PlaybackModel.Listener {
        override fun onUpdate(state: PlaybackState) {
            onPlaybackStateUpdate(state)
        }

        override fun onError(error: Throwable) {}

        override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {}
    }
}
