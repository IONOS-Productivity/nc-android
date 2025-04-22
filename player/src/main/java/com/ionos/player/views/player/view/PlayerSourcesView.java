package com.ionos.player.views.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

import com.ionos.player.R;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.player_mode.PlayerMode;
import com.ionos.player.player_source_release_strategy.DoNotReleaseIfExistsSourceInfoReleaseStrategy;
import com.ionos.player.util.Cast;
import com.ionos.player.views.infinite_view_pager.InfiniteViewPager;
import com.ionos.player.views.infinite_view_pager.Mode;
import com.ionos.player.views.infinite_view_pager.ViewPagerFragmentFactory;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.presenter.MultiplePlayerSourcesPresenter;
import com.ionos.player.multipleplayermvp.presenter_destroy_strategy.DoNothingMultiplePlayerPresenterDestroyStrategy;
import com.ionos.player.player.interfaces.PlayerExceptionMessageProvider;

import java.util.List;

import javax.inject.Inject;

import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by yaz on 9/20/16.
 */
public class PlayerSourcesView extends LinearLayout {

	@Inject
	MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> playerModel;
	@Inject
	PlayerExceptionMessageProvider playerExceptionMessageProvider;

	private MultiplePlayer.SourcesPresenter<PlayerFileInfo> presenter;
	private final InfiniteViewPager<PlayerFileInfo> infiniteViewPager;
	private FragmentActivity activity;

	public PlayerSourcesView(Context context) {
		this(context, null);
	}

	public PlayerSourcesView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlayerSourcesView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflate(context, R.layout.view_player_sources, this);
		this.infiniteViewPager = findViewById(R.id.infiniteViewPager);
		if (isInEditMode()) {
			return;
		}
		PlayerComponent.Companion.from(context).inject(this);
		this.presenter = new MultiplePlayerSourcesPresenter<>(
				this.playerModel,
				new DoNothingMultiplePlayerPresenterDestroyStrategy<>(),
				new DoNotReleaseIfExistsSourceInfoReleaseStrategy(),
				playerExceptionMessageProvider
		);

		this.activity = Cast.castOrError(context, FragmentActivity.class);
		this.infiniteViewPager.setInfiniteViewPagerListener(item -> presenter.onSwitchToSourceInfo(item));
	}

	public void addViewPagerOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		this.infiniteViewPager.addViewPagerOnPageChangeListener(listener);
	}

	public void removeViewPagerOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		this.infiniteViewPager.addViewPagerOnPageChangeListener(listener);
	}

	public void init(ViewPagerFragmentFactory<PlayerFileInfo> fragmentFactory) {
		this.infiniteViewPager.init(activity.getSupportFragmentManager(), Mode.INFINITE, fragmentFactory);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (isInEditMode()) {
			return;
		}
		this.presenter.setView(this.view);
		this.presenter.onCreate();
	}

	@Override
	protected void onDetachedFromWindow() {
		if (isInEditMode()) {
			return;
		}
		this.presenter.onDestroy();
		this.presenter.setView(null);
		super.onDetachedFromWindow();
	}

	public void onStart() {
		this.presenter.onAppear();
	}

	public void onStop() {
		this.presenter.onDisappear();
	}

	@Override
	public WindowInsets onApplyWindowInsets(WindowInsets insets) {
		return WindowInsetsCompat.CONSUMED.toWindowInsets();
	}

	private final MultiplePlayer.SourcesView<PlayerFileInfo> view = new MultiplePlayer.SourcesView<>() {

		@Override
		public void displayCurrentSourceInfo(PlayerFileInfo sourceInfo) {
			infiniteViewPager.setCurrentItem(sourceInfo);
		}

		@Override
		public void displaySourceInfos(List<PlayerFileInfo> sourceInfos) {
			if (!infiniteViewPager.getItems().equals(sourceInfos)) {
				infiniteViewPager.setItems(sourceInfos);
			}
		}
	};
}

