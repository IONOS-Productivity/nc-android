package com.ionos.player.ui.audio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionos.player.R;
import com.ionos.player.model.PlaybackFile;
import com.ionos.player.ui.common.FileTextDetailView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Sergey Shandyuk on 4/15/2016.
 */

public class AudioPlayerSourceFragment extends Fragment {

	private final static String ARGUMENT_FILE = "ARGUMENT_FILE";

	private PlaybackFile file;

	private View coverContainer;
	private View mediaContainer;

	public static Fragment createInstance(PlaybackFile file) {
		AudioPlayerSourceFragment fragment = new AudioPlayerSourceFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARGUMENT_FILE, file);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidSupportInjection.inject(this);
		this.file = (PlaybackFile) getArguments().getSerializable(ARGUMENT_FILE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_audio_player_source, container, false);

		this.coverContainer = content.findViewById(R.id.songContainer);
		this.mediaContainer = content.findViewById(R.id.videoContainer);
		FileTextDetailView fileTextDetailView = content.findViewById(R.id.fileDetailView);

		fileTextDetailView.displayFile(this.file);
		switchToSongContainer();

		return content;
	}

	private void switchToSongContainer() {
		this.coverContainer.setVisibility(View.VISIBLE);
		this.mediaContainer.setVisibility(View.INVISIBLE);
	}
}
