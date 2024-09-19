package com.viseven.develop.multipleplayermvp.presenter;

import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;
import com.viseven.develop.multipleplayermvp.null_object.NullMultiplePlayerHidingView;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerHidingPresenter<SourceInfo, Mode> implements MultiplePlayer.HidingPresenter<SourceInfo> {

	private final MultiplePlayer.Model<SourceInfo, Mode> model;
	private MultiplePlayer.HidingView<SourceInfo> view = NullMultiplePlayerHidingView.getInstance();

	public MultiplePlayerHidingPresenter(MultiplePlayer.Model<SourceInfo, Mode> model) {
		this.model = model;
	}

	@Override
	public void setView(MultiplePlayer.HidingView<SourceInfo> view) {
		this.view = view != null ? view : NullMultiplePlayerHidingView.<SourceInfo>getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerHidingView.getInstance();
	}

	@Override
	public void onAppear() {
		updateView();
		this.model.addListener(this.listener);
	}

	@Override
	public void onDisappear() {
		this.model.removeListener(this.listener);
	}

	private void updateView() {
		boolean hasSources = this.model.getState()
				.mapToBoolean(input -> !input.currentSourceInfos.isEmpty())
				.orElse(false);
		if (hasSources) {
			this.view.displayPlayerView();
		} else {
			this.view.doNotDisplayPlayerView();
		}
	}

	private final MultiplePlayer.Model.Listener<SourceInfo, Mode> listener = new MultiplePlayer.Model.Listener<SourceInfo, Mode>() {
		@Override
		public void onUpdate(MultiplePlaybackState<SourceInfo, Mode> state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {
			updateView();
		}
	};
}
