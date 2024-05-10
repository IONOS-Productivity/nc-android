package com.strato.hidrive.common_ui.view.contextbar.toolbar;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.strato.hidrive.domain.optional.Optional;

import java.util.Objects;

/**
 * Created by zuzik on 10.12.2015.
 */

public class ToolbarItemBundle {

	@DrawableRes
	public final int toolbarImageResId;
	public final Optional<Integer> toolbarTintColorResId;
	@StringRes
	public final int titleResId;
	public final ToolbarItemViewType itemViewType;

	public static ToolbarItemBundle createBundleForToolbar(@DrawableRes int toolbarImageResId) {
		return new ToolbarItemBundle(toolbarImageResId, 0, ToolbarItemViewType.IMAGE);
	}

	public static ToolbarItemBundle createBundleForToolbar(@DrawableRes int toolbarImageResId, ToolbarItemViewType itemViewType) {
		return new ToolbarItemBundle(toolbarImageResId, 0, itemViewType);
	}

	public static ToolbarItemBundle createTextBundleForToolbar(@StringRes int titleResId) {
		return new ToolbarItemBundle(0, titleResId, ToolbarItemViewType.TEXT);
	}

	public static ToolbarItemBundle createBundleForPopup(@StringRes int titleResId) {
		return new ToolbarItemBundle(0, titleResId, ToolbarItemViewType.DEFAULT);
	}

	public static boolean isTextToolbarItem(ToolbarItem item){
		return item.getToolbarImageResId() == 0;
	}

	public static ToolbarItemBundle createBundleForFab(@DrawableRes int imageResId, @StringRes int titleResId) {
		return new ToolbarItemBundle(imageResId, titleResId, ToolbarItemViewType.IMAGE);
	}

	public static ToolbarItemBundle createBundleForFab(@DrawableRes int imageResId, @ColorRes int tintColorResId, @StringRes int titleResId) {
		return new ToolbarItemBundle(imageResId, Optional.of(tintColorResId), titleResId, ToolbarItemViewType.IMAGE);
	}

	public static ToolbarItemBundle empty() {
		return new ToolbarItemBundle(0, 0, ToolbarItemViewType.DEFAULT);
	}

	private ToolbarItemBundle(@DrawableRes int toolbarImageResId, Optional<Integer> toolbarTintColorResId, @StringRes int titleResId, ToolbarItemViewType itemViewType) {
		this.toolbarImageResId = toolbarImageResId;
		this.toolbarTintColorResId = toolbarTintColorResId;
		this.titleResId = titleResId;
		this.itemViewType = itemViewType;
	}

	private ToolbarItemBundle(@DrawableRes int toolbarImageResId, @StringRes int titleResId, ToolbarItemViewType itemViewType) {
		this(toolbarImageResId, Optional.absent(), titleResId, itemViewType);
	}

	private ToolbarItemBundle(ToolbarItemBundle toolbarItemBundle) {
		this(
				toolbarItemBundle.toolbarImageResId,
				toolbarItemBundle.toolbarTintColorResId,
				toolbarItemBundle.titleResId,
				toolbarItemBundle.itemViewType
		);
	}

	public ToolbarItemBundle copy() {
		return new ToolbarItemBundle(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ToolbarItemBundle)) return false;
		ToolbarItemBundle that = (ToolbarItemBundle) o;
		return this.toolbarImageResId == that.toolbarImageResId &&
				Objects.equals(this.toolbarTintColorResId, that.toolbarTintColorResId) &&
				this.titleResId == that.titleResId &&
				Objects.equals(this.itemViewType, that.itemViewType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.toolbarImageResId,
				this.toolbarTintColorResId,
				this.titleResId,
				this.itemViewType);
	}
}
