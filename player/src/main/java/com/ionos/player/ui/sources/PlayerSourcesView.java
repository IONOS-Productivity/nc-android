package com.ionos.player.ui.sources;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

import com.ionos.player.R;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.release_strategy.DoNotReleaseIfExistsSourceInfoReleaseStrategy;
import com.ionos.player.ui.message.PlayerExceptionMessageProvider;
import com.ionos.player.ui.pager.InfiniteViewPager;
import com.ionos.player.ui.pager.Mode;
import com.ionos.player.ui.pager.ViewPagerFragmentFactory;
import com.ionos.player.ui.sources.destroy_strategy.DoNothingMultiplePlayerPresenterDestroyStrategy;
import com.ionos.player.util.Cast;

import java.util.List;

import javax.inject.Inject;

import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import dagger.android.HasAndroidInjector;

/**
 * Created by yaz on 9/20/16.
 */
public class PlayerSourcesView extends LinearLayout {

	@Inject
	MultiplePlayer.Model<PlayerFileInfo> playerModel;
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
        ((HasAndroidInjector) context.getApplicationContext()).androidInjector().inject(this);
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

