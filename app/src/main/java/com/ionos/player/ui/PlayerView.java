/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ionos.player.model.PlaybackFile;

import androidx.annotation.Nullable;

public abstract class PlayerView extends LinearLayout {

	public interface CurrentFileListener{
		void fileChanged(PlaybackFile file);
	}

	@Nullable
	protected CurrentFileListener currentFileListener;

	public PlayerView(Context context) {
		super(context);
	}

	public PlayerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public abstract void onStart();

	public abstract void onStop();

	public void setCurrentFileListener(@Nullable CurrentFileListener listener){
		this.currentFileListener = listener;
	}

}
