package com.ionos.player.views.player.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionos.player.R;
import com.ionos.player.chromecast.PlayerChromecastModel;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.player_mode.EmptyPlayerModeModel;
import com.ionos.player.player_mode.PlayerMode;
import com.ionos.player.player_mode.PlayerModeModel;
import com.ionos.player.player_mode.PlayerModePresenter;
import com.ionos.player.views.player.view.FileTextDetailView;

import java.util.Optional;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Sergey Shandyuk on 4/15/2016.
 */

public class AudioPlayerSourceFragment extends Fragment {

	private final static String ARGUMENT_FILE_INFO = "ARGUMENT_FILE_INFO";

	@Inject
	Optional<PlayerChromecastModel> chromecastModel;

	private PlayerMode.Presenter playerModePresenter;

	private PlayerFileInfo fileInfo;

	private View coverContainer;
	private View mediaContainer;

	public static Fragment createInstance(PlayerFileInfo fileInfo) {
		AudioPlayerSourceFragment fragment = new AudioPlayerSourceFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARGUMENT_FILE_INFO, fileInfo);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PlayerComponent.Companion.from(requireContext()).inject(this);
		this.fileInfo = (PlayerFileInfo) getArguments().getSerializable(ARGUMENT_FILE_INFO);

		PlayerMode.Model playerModeModel;
		if (chromecastModel.isPresent()){
			playerModeModel = new PlayerModeModel(this.chromecastModel.get());
		}else {
			playerModeModel = new EmptyPlayerModeModel();
		}
		this.playerModePresenter = new PlayerModePresenter(playerModeModel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_audio_player_source, container, false);

		this.coverContainer = content.findViewById(R.id.songContainer);
		this.mediaContainer = content.findViewById(R.id.videoContainer);
		FileTextDetailView fileTextDetailView = content.findViewById(R.id.fileDetailView);

		fileTextDetailView.displayFileInfo(this.fileInfo);
		switchToSongContainer();

		return content;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.playerModePresenter.onCreate();
	}

	@Override
	public void onDestroyView() {
		this.playerModePresenter.onDestroy();
		super.onDestroyView();
	}

	@Override
	public void onStart() {
		super.onStart();
		this.playerModePresenter.onAppear();
	}

	@Override
	public void onStop() {
		this.playerModePresenter.onDisappear();
		super.onStop();
	}


	private void switchToSongContainer() {
		this.coverContainer.setVisibility(View.VISIBLE);
		this.mediaContainer.setVisibility(View.INVISIBLE);
	}
}
