package com.ionos.player.ui.audio

import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.state.PlaybackItemState
import com.ionos.player.model.state.PlaybackState
import com.owncloud.android.R
import com.owncloud.android.databinding.PlayerAudioSourceFragmentBinding
import dagger.android.support.AndroidSupportInjection
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

    private lateinit var binding: PlayerAudioSourceFragmentBinding
    private lateinit var file: PlaybackFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        file = arguments?.getSerializable(ARGUMENT_FILE) as PlaybackFile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PlayerAudioSourceFragmentBinding.inflate(inflater, container, false)
        binding.title.text = file.getNameWithoutExtension()
        binding.fileDetails.text = file.getDetailsText()
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

    private fun PlaybackFile.getDetailsText(): String {
        fun formatDate(timestamp: Long) = DateFormat.getDateFormat(context).format(Date(timestamp))
        val size = if (contentLength > 0) Formatter.formatFileSize(context, contentLength) else ""
        val date = if (lastModified > 0) getString(R.string.player_last_change_date, formatDate(lastModified)) else ""
        return if (size.isNotEmpty() && date.isNotEmpty()) "$size, $date" else size + date
    }

    private val playbackModelListener = object : PlaybackModel.Listener {
        override fun onUpdate(state: PlaybackState) {
            state.currentItemState.map(PlaybackItemState::metadata).ifPresent { metadata ->
                binding.title.text = metadata.title
                binding.artist.text = metadata.artist
                binding.artist.visibility = if (metadata.artist.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        }

        override fun onError(error: Throwable) {}

        override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {}
    }
}
