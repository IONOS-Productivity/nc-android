package com.strato.hidrive.player.views.multiple_click;

import android.os.Handler;
import android.view.View;

import java.util.Optional;


/**
 * Created by yaz on 1/25/17.
 */

public abstract class MultipleClickListener implements View.OnClickListener {

	private static final int TIME_WINDOW_FOR_CLICK_DETERMINATION_IN_MILLISECONDS = 250;

	private final Handler handler = new Handler();
	private Optional<Integer> clicksCount = Optional.empty();

	protected abstract void onSingleClick(View view);
	protected abstract void onDoubleClick(View view);

	@Override
	public final void onClick(final View view) {
		boolean interactionIsBegan = clicksCount.isPresent();

		if (interactionIsBegan) {
			clicksCount = Optional.of(clicksCount.get() + 1);
		} else {
			clicksCount = Optional.of(1);
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					int count = clicksCount.get();
					clicksCount = Optional.empty();
					callSubscriber(view, count);
				}
			}, TIME_WINDOW_FOR_CLICK_DETERMINATION_IN_MILLISECONDS);
		}
	}

	private void callSubscriber(View view, int clicksCount) {
		if (clicksCount == 1) {
			onSingleClick(view);
		} else {
			onDoubleClick(view);
		}
	}
}
