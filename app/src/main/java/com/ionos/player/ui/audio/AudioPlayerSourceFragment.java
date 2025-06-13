package com.ionos.player.ui.audio;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionos.player.model.PlaybackFile;
import com.owncloud.android.R;
import com.owncloud.android.databinding.PlayerAudioSourceFragmentBinding;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Sergey Shandyuk on 4/15/2016.
 */

public class AudioPlayerSourceFragment extends Fragment {

	private final static String ARGUMENT_FILE = "ARGUMENT_FILE";

	private PlaybackFile file;

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
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PlayerAudioSourceFragmentBinding binding = PlayerAudioSourceFragmentBinding.inflate(inflater, container, false);
		binding.title.setText(file.getNameWithoutExtension());
		binding.fileDetails.setText(getFileDetailsText());
		return binding.getRoot();
	}

    private String getFileDetailsText() {
        StringBuilder stringBuilder = new StringBuilder();
        if (file.getContentLength() > 0) {
            stringBuilder.append(Formatter.formatFileSize(getContext(), file.getContentLength()));
        }
        if (file.getLastModified() > 0) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(", ");
            }
            stringBuilder
                .append(getResources().getString(R.string.player_last_change_date))
                .append(" ")
                .append(DateFormat.getDateFormat(getContext()).format(new Date(file.getLastModified())));
        }
        return stringBuilder.toString();
    }
}
