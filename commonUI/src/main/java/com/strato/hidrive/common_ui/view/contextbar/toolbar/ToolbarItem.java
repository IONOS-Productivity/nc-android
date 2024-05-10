package com.strato.hidrive.common_ui.view.contextbar.toolbar;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.strato.hidrive.domain.optional.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by y.zozulia on 09.12.2015.
 */

public class ToolbarItem {

	private ToolbarItemBundle uncheckedStateBundle = ToolbarItemBundle.empty();
	private ToolbarItemBundle checkedStateBundle = ToolbarItemBundle.empty();
	private final ToolbarItemType type;
	private final ToolbarItemPosition position;
	private boolean isCheckable;
	private boolean checked;
	private boolean isActive = true;

	public static ToolbarItem createItemForToolbar(ToolbarItemType type, @DrawableRes int toolbarImageResId) {
		return new ToolbarItem(type,
				ToolbarItemPosition.TOOLBAR,
				ToolbarItemBundle.createBundleForToolbar(toolbarImageResId));
	}

	public static ToolbarItem createMoreMenuItemForToolbar(@DrawableRes int toolbarImageResId) {
		return new ToolbarItem(
				ToolbarItemType.MORE,
				ToolbarItemPosition.TOOLBAR,
				ToolbarItemBundle.createBundleForToolbar(toolbarImageResId));
	}

	public static ToolbarItem createItemForToolbar(ToolbarItemType type, @DrawableRes int toolbarImageResId, ToolbarItemViewType itemViewType) {
		return new ToolbarItem(type,
				ToolbarItemPosition.TOOLBAR,
				ToolbarItemBundle.createBundleForToolbar(toolbarImageResId, itemViewType));
	}

	public static ToolbarItem createStringItemForToolbar(ToolbarItemType type, @StringRes int titleResId) {
		return new ToolbarItem(type,
				ToolbarItemPosition.TOOLBAR,
				ToolbarItemBundle.createTextBundleForToolbar(titleResId));
	}

	public static ToolbarItem createItemForPopup(ToolbarItemType type, @StringRes int titleResId) {
		return new ToolbarItem(type,
				ToolbarItemPosition.POPUP,
				ToolbarItemBundle.createBundleForPopup(titleResId));
	}

	public static ToolbarItem createItemForFab(ToolbarItemType type, @DrawableRes int imageResId, @StringRes int titleResId) {
		return new ToolbarItem(type,
				ToolbarItemPosition.FAB,
				ToolbarItemBundle.createBundleForFab(imageResId, titleResId));
	}

	public static ToolbarItem createItemForFab(ToolbarItemType type, @DrawableRes int imageResId, @ColorRes int tintColorResId, @StringRes int titleResId) {
		return new ToolbarItem(type,
				ToolbarItemPosition.FAB,
				ToolbarItemBundle.createBundleForFab(imageResId, tintColorResId, titleResId));
	}

	public static ToolbarItem createEmptyToolbarItem(ToolbarItemType type) {
		return new ToolbarItem(type, ToolbarItemPosition.NONE, ToolbarItemBundle.empty());
	}

	private ToolbarItem(ToolbarItemType type, ToolbarItemPosition position, ToolbarItemBundle uncheckedStateBundle) {
		this.type = type;
		this.position = position;
		this.uncheckedStateBundle = uncheckedStateBundle;
	}

	private ToolbarItem(ToolbarItemType type, ToolbarItemPosition position, ToolbarItemBundle uncheckedStateBundle, ToolbarItemBundle checkedStateBundle, boolean checked) {
		this(type, position, uncheckedStateBundle);
		this.isCheckable = true;
		this.checkedStateBundle = checkedStateBundle;
		this.checked = checked;
	}

	private ToolbarItem(ToolbarItem item) {
		this.uncheckedStateBundle = item.uncheckedStateBundle.copy();
		this.checkedStateBundle = item.checkedStateBundle.copy();
		this.type = item.type;
		this.position = item.position;
		this.isCheckable = item.isCheckable;
		this.checked = item.checked;
	}

	public ToolbarItemType getType() {
		return this.type;
	}

	@DrawableRes
	public int getToolbarImageResId() {
		return getToolbarItemBundleForCheckedState().toolbarImageResId;
	}

	public Optional<Integer> getToolbarImageTintColorResId() {
		return getToolbarItemBundleForCheckedState().toolbarTintColorResId;
	}

	@StringRes
	public int getTitleResId() {
		return getToolbarItemBundleForCheckedState().titleResId;
	}

	public ToolbarItemViewType getItemViewType() {
		return getToolbarItemBundleForCheckedState().itemViewType;
	}

	public ToolbarItemPosition getPosition() {
		return this.position;
	}

	private ToolbarItemBundle getToolbarItemBundleForCheckedState() {
		return this.checked ? this.checkedStateBundle : this.uncheckedStateBundle;
	}

	public void setChecked() {
		if (this.isCheckable) {
			this.checked = true;
		}
	}

	public void setUnchecked() {
		if (this.isCheckable) {
			this.checked = false;
		}
	}

	public ToolbarItem copy() {
		return new ToolbarItem(this);
	}

	public static List<ToolbarItem> filter(List<ToolbarItem> items, ToolbarItemPosition filter) {
		List<ToolbarItem> toolbarItems = new ArrayList<>();
		for (ToolbarItem item : items) {
			if (item.getPosition() == filter) {
				toolbarItems.add(item);
			}
		}
		return toolbarItems;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ToolbarItem)) return false;
		ToolbarItem that = (ToolbarItem) o;
		return Objects.equals(this.uncheckedStateBundle, that.uncheckedStateBundle) &&
				Objects.equals(this.checkedStateBundle, that.checkedStateBundle) &&
				this.type == that.type &&
				this.position == that.position &&
				this.isCheckable == that.isCheckable &&
				this.checked == that.checked &&
				this.isActive == that.isActive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.uncheckedStateBundle,
				this.checkedStateBundle,
				this.type,
				this.position,
				this.isCheckable,
				this.checked,
				this.isActive);
	}
}
