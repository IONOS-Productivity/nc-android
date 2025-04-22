package com.ionos.player.views.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ionos.player.domain.PlayerFileInfo;

import androidx.annotation.Nullable;

/**
 * Created by Anton Shevchuk on 28.12.2016.
 */

public abstract class PlayerView extends LinearLayout {

	public interface CurrentFileListener{
		void fileChanged(PlayerFileInfo fileInfo);
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
