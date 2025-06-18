package com.ionos.player.ui.audio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.ui.MultiplePlayer;
import com.ionos.player.ui.MultiplePlayerHidingPresenter;
import com.ionos.player.ui.PlayerView;
import com.ionos.player.ui.PlayerViewContainer;
import com.ionos.player.ui.control.PlayerControlView;
import com.ionos.player.ui.sources.PlayerSourcesView;
import com.ionos.player.util.Cast;
import com.ionos.player.util.WindowWrapper;
import com.owncloud.android.R;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import dagger.android.HasAndroidInjector;

/**
 * Created by yaz on 9/20/16.
 */
public class AudioPlayerView extends PlayerView {

	@Inject
    PlaybackModel playerModel;

    private final ViewGroup topBar;
	private final TextView tvTitle;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter hidingPresenter;

	public AudioPlayerView(Context context) {
		super(context);
		inflate(context, R.layout.player_audio_view, this);

        this.topBar = findViewById(R.id.topBar);
		this.playerSourcesView = findViewById(R.id.playerSourcesView);
		this.playerControlView = findViewById(R.id.playerControlView);
		this.tvTitle = findViewById(R.id.tvTitle);

		ImageView ivBack = findViewById(R.id.ivBack);

		if (isInEditMode()) {
			return;
		}

		inject(context);

		this.playerViewContainer = Cast.castOrError(context, PlayerViewContainer.class);

		ivBack.setOnClickListener(view -> {
			playerViewContainer.onPlayerViewClose();
		});

		this.hidingPresenter = new MultiplePlayerHidingPresenter(this.playerModel);

		this.playerSourcesView.init(new AudioPlayerSourceFragmentFactory());

		Cast.castOrError(context, Activity.class).setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

	protected void inject(@NonNull Context context) {
        ((HasAndroidInjector) context.getApplicationContext()).androidInjector().inject(this);
	}

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets);
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        this.topBar.setPadding(insets.left, insets.top, insets.right, 0);
        this.playerSourcesView.setPadding(insets.left, 0, insets.right, 0);
        this.playerControlView.setPadding(insets.left, 0, insets.right, insets.bottom);

        Activity activity = Cast.castOrError(getContext(), Activity.class);
        if (activity != null) {
            WindowWrapper windowWrapper = new WindowWrapper(activity.getWindow());
            windowWrapper.setupStatusBar(R.color.player_full_screen_audio_player_bar, false);
            windowWrapper.setupNavigationBar(R.color.player_full_screen_audio_player_background, true);
        }

        return WindowInsetsCompat.CONSUMED.toWindowInsets();
    }

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (isInEditMode()) {
			return;
		}
		this.hidingPresenter.setView(this.hidingView);
		this.hidingPresenter.onCreate();
	}

	@Override
	protected void onDetachedFromWindow() {
		if (isInEditMode()) {
			return;
		}
		this.hidingPresenter.setView(null);
		this.hidingPresenter.onDestroy();
		super.onDetachedFromWindow();
	}

	@CallSuper
	@Override
	public void onStart() {
		this.playerSourcesView.onStart();
		this.playerControlView.onStart();
		this.hidingPresenter.onAppear();
		this.playerModel.addListener(this.playerModelListener);
	}

	@CallSuper
	@Override
	public void onStop() {
		this.playerSourcesView.onStop();
		this.playerControlView.onStop();
		this.hidingPresenter.onDisappear();
		this.playerModel.removeListener(this.playerModelListener);
	}

	protected void updateState() {
		if (!this.playerModel.getState().isPresent()) {
			playerViewContainer.onPlayerViewClose();
			return;
		}
		PlaybackState state = this.playerModel.getState().get();
		if (state.currentItemState.isPresent()) {
			PlaybackItemState playbackItemState = state.currentItemState.get();
			PlaybackFile file = playbackItemState.file;
			if (currentFileListener != null) {
				currentFileListener.fileChanged(file);
			}
			this.tvTitle.setText(file.getNameWithoutExtension());
		} else {
			this.tvTitle.setText("");
		}
	}

	private final PlaybackModel.Listener playerModelListener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {
			updateState();
		}

		@Override
		public void onError(Throwable error) { /**/ }

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) { /**/ }
	};

	private final MultiplePlayer.HidingView hidingView = new MultiplePlayer.HidingView() {
		@Override
		public void displayPlayerView() { /**/ }

		@Override
		public void doNotDisplayPlayerView() {
			playerViewContainer.onPlayerViewClose();
		}
	};
}
