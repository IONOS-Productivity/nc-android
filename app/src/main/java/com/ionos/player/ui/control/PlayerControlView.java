/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ionos.player.model.PlaybackModel;
import com.ionos.player.ui.control.listener.MultipleClickListener;
import com.ionos.player.ui.control.listener.PlayerControlViewCompositeListener;
import com.ionos.player.ui.control.listener.PlayerControlViewListener;
import com.owncloud.android.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import dagger.android.HasAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.subjects.PublishSubject;

public class PlayerControlView extends LinearLayout implements PlayerControl.View {

    private static final String INDETERMINATE_TIME = "--:--";

    private static final String TAG_CLICK_COMMAND_PLAY = "TAG_CLICK_COMMAND_PLAY";
    private static final String TAG_CLICK_COMMAND_PAUSE = "TAG_CLICK_COMMAND_PAUSE";

    private static final String TAG_CLICK_COMMAND_REPEAT = "TAG_CLICK_COMMAND_REPEAT";
    private static final String TAG_CLICK_COMMAND_DO_NOT_REPEAT = "TAG_CLICK_COMMAND_DO_NOT_REPEAT";

    private static final String TAG_CLICK_COMMAND_SHUFFLE = "TAG_CLICK_COMMAND_SHUFFLE";
    private static final String TAG_CLICK_COMMAND_DO_NOT_SHUFFLE = "TAG_CLICK_COMMAND_DO_NOT_SHUFFLE";

    private static final String TAG_CLICK_COMMAND_UNKNOWN = "TAG_CLICK_COMMAND_UNKNOWN";

    @Inject
    PlaybackModel playerModel;

    private PlayerControl.Presenter controlPresenter;
    private final PublishSubject<Integer> seekBarProgressChangePublishSubject = PublishSubject.create();
    private Disposable seekBarProgressChangeDisposable = Disposables.disposed();

    private final TextView tvElapsed;
    private final TextView tvTotalTime;
    private final SeekBar progressBar;
    private final ImageView ivRandom;
    private final ImageView ivRepeat;
    private final ImageView ivPrevious;
    private final ImageView ivNext;
    private final ImageView ivPlayPause;

    private final PlayerControlViewCompositeListener compositeListener = new PlayerControlViewCompositeListener();

    public PlayerControlView(Context context) {
        this(context, null);
    }

    public PlayerControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.player_control_view, this);
        this.tvElapsed = findViewById(R.id.tvElapsed);
        this.tvTotalTime = findViewById(R.id.tvTotalTime);
        this.progressBar = findViewById(R.id.progressBar);
        this.ivRandom = findViewById(R.id.ivRandom);
        this.ivRepeat = findViewById(R.id.ivRepeat);
        this.ivPrevious = findViewById(R.id.ivPrevious);
        this.ivNext = findViewById(R.id.ivNext);
        this.ivPlayPause = findViewById(R.id.ivPlayPause);

        setProgress(0, 0);

        if (isInEditMode()) {
            return;
        }

        ((HasAndroidInjector) context.getApplicationContext()).androidInjector().inject(this);

        this.controlPresenter = new PlayerControlPresenter(this.playerModel);

        setDefaultTags();
        setListeners();
    }

    public void addListener(PlayerControlViewListener listener) {
        this.compositeListener.addListener(listener);
    }

    public void removeListener(PlayerControlViewListener listener) {
        this.compositeListener.removeListener(listener);
    }

    private void setDefaultTags() {
        this.ivPlayPause.setTag(TAG_CLICK_COMMAND_UNKNOWN);
        this.ivRandom.setTag(TAG_CLICK_COMMAND_UNKNOWN);
        this.ivRepeat.setTag(TAG_CLICK_COMMAND_UNKNOWN);
    }

    private void setListeners() {
        this.ivPlayPause.setOnClickListener(view -> handlePlayPauseClick());
        this.ivNext.setOnClickListener(view -> {
            controlPresenter.onNextClicked();
            compositeListener.onNextClicked();
        });
        this.ivPrevious.setOnClickListener(new MultipleClickListener() {
            @Override
            protected void onSingleClick(View view) {
                controlPresenter.onPreviousClicked();
                compositeListener.onPreviousClicked();
            }

            @Override
            protected void onDoubleClick(View view) {
                controlPresenter.onPreviousDoubleClicked();
                compositeListener.onPreviousClicked();
            }
        });

        this.ivRepeat.setOnClickListener(view -> handleRepeatClick());
        this.ivRandom.setOnClickListener(view -> handleShuffleClick());
        this.progressBar.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
    }

    private void subscribeToSeekBarProgressChange() {
        seekBarProgressChangeDisposable = seekBarProgressChangePublishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(progress -> {
                controlPresenter.onSeekToPosition(progress);
                compositeListener.onProgressChangedByUser(progress);
            });
    }

    private void unsubscribeFromSeekBarProgressChange() {
        seekBarProgressChangeDisposable.dispose();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        this.controlPresenter.setView(this);
        this.controlPresenter.onCreate();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isInEditMode()) {
            return;
        }
        this.controlPresenter.onDestroy();
        this.controlPresenter.setView(null);
        unsubscribeFromSeekBarProgressChange();
        super.onDetachedFromWindow();
    }

    public void onStart() {
        subscribeToSeekBarProgressChange();
        this.controlPresenter.onAppear();
    }

    public void onStop() {
        unsubscribeFromSeekBarProgressChange();
        this.controlPresenter.onDisappear();
    }

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                seekBarProgressChangePublishSubject.onNext(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            compositeListener.onProgressStopTrackingTouch();
        }
    };

    private void handlePlayPauseClick() {
        if (TAG_CLICK_COMMAND_PLAY.equals(ivPlayPause.getTag())) {
            controlPresenter.onPlay();
            compositeListener.onPlayClicked();
        } else if (TAG_CLICK_COMMAND_PAUSE.equals(ivPlayPause.getTag())) {
            controlPresenter.onPause();
            compositeListener.onPauseClicked();
        } else {
            Log.w(getClass().getSimpleName(), "Unreachable playPause button state");
        }
    }

    private void handleRepeatClick() {
        if (TAG_CLICK_COMMAND_REPEAT.equals(ivRepeat.getTag())) {
            controlPresenter.onRepeat();
            compositeListener.onRepeatClicked();
        } else if (TAG_CLICK_COMMAND_DO_NOT_REPEAT.equals(ivRepeat.getTag())) {
            controlPresenter.onDoNotRepeat();
            compositeListener.onDoNotRepeatClicked();
        } else {
            Log.w(getClass().getSimpleName(), "Unreachable repeat button state");
        }
    }

    private void handleShuffleClick() {
        if (TAG_CLICK_COMMAND_SHUFFLE.equals(ivRandom.getTag())) {
            controlPresenter.onShuffle();
            compositeListener.onShuffleClicked();
        } else if (TAG_CLICK_COMMAND_DO_NOT_SHUFFLE.equals(ivRandom.getTag())) {
            controlPresenter.onDoNotShuffle();
            compositeListener.onDoNotShuffleClicked();
        } else {
            Log.w(getClass().getSimpleName(), "Unreachable shuffle button state");
        }
    }

    @Override
    public void repeat() {
        setImageTint(ivRepeat, R.color.player_accent_color);
        ivRepeat.setTag(TAG_CLICK_COMMAND_DO_NOT_REPEAT);
    }

    @Override
    public void doNotRepeat() {
        setImageTint(ivRepeat, R.color.player_default_icon_color);
        ivRepeat.setTag(TAG_CLICK_COMMAND_REPEAT);
    }

    @Override
    public void shuffle() {
        setImageTint(ivRandom, R.color.player_accent_color);
        ivRandom.setTag(TAG_CLICK_COMMAND_DO_NOT_SHUFFLE);
    }

    @Override
    public void doNotShuffle() {
        setImageTint(ivRandom, R.color.player_default_icon_color);
        ivRandom.setTag(TAG_CLICK_COMMAND_SHUFFLE);
    }

    @Override
    public void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds) {
        progressBar.setMax(totalTimeInMilliseconds);
        progressBar.setProgress(currentTimeInMilliseconds);
        tvElapsed.setText(formatTime(currentTimeInMilliseconds));
        tvTotalTime.setText(formatTime(totalTimeInMilliseconds));
    }

    @Override
    public void setProgressAvailable() {
        progressBar.setEnabled(true);
    }

    @Override
    public void setProgressNotAvailable() {
        progressBar.setEnabled(false);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        tvElapsed.setText(INDETERMINATE_TIME);
        tvTotalTime.setText(INDETERMINATE_TIME);
    }

    @Override
    public void enablePlayControls(boolean play, boolean pause, boolean stop) {
        if (pause) {
            configurePlayPauseButton(R.drawable.player_ic_pause, TAG_CLICK_COMMAND_PAUSE);
        } else {
            configurePlayPauseButton(R.drawable.player_ic_play, TAG_CLICK_COMMAND_PLAY);
        }
    }

    @Override
    public void enableSwitchControls(boolean next, boolean previous) {
        ivNext.setEnabled(next);
        ivPrevious.setEnabled(previous);
    }

    private void configurePlayPauseButton(@DrawableRes int imageResource, String tagState) {
        ivPlayPause.setImageResource(imageResource);
        ivPlayPause.setTag(tagState);
    }

    private void setImageTint(ImageView imageView, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(getContext(), colorRes);
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color));
    }

    private String formatTime(int timeMillis) {
        int timeSeconds = timeMillis / 1000;
        int seconds = timeSeconds % 60;
        int minutes = timeSeconds / 60;
        int hours = minutes / 60;
        if (hours > 0) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes % 60, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }
}
