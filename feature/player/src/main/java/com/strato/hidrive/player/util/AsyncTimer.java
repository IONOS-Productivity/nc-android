package com.strato.hidrive.player.util;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class AsyncTimer {

	public interface TimerCallbackListener {
		void timeComplitted();
	}

	private Timer timer;
	private final Handler handler;
	private TimerCallbackListener callbackListener;
	private final long timeDuration;
	private boolean isRunning;

	public AsyncTimer(Long timeDuration) {
		this.timeDuration = timeDuration;

		this.timer = new Timer();
		this.handler = new Handler();
	}

	public void subscribe(TimerCallbackListener callbackListener) {
		this.callbackListener = callbackListener;
	}

	public void unSubscribe() {
		this.callbackListener = null;
	}

	public void start() {
		this.timer = new Timer();

		this.timer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(new Runnable() {

					@Override
					public void run() {
						if (callbackListener != null) {
							callbackListener.timeComplitted();
						}
					}
				});
			}
		}, this.timeDuration, this.timeDuration);
		this.isRunning = true;
	}

	public void stop() {
		this.timer.cancel();
		this.isRunning = false;
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}
