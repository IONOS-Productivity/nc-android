package com.strato.hidrive.player.player_mode;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * Created by: Alex Kucherenko
 * Date: 23.02.2017.
 */

public interface PlayerMode {

	interface Model {

		PlayerMode.Mode getMode();

		void init();

		void release();

		void setListener(Listener listener);

		interface Listener {
			void onModeChanged(Mode mode);
		}
	}

	interface View {
		void switchToRegularMode();

		void switchToChromecastMode();
	}

	interface Presenter extends MultiplePlayer.BasePresenter {
		void setView(View view);
	}

	enum Mode {
		REGULAR,
		CHROMECAST
	}
}
