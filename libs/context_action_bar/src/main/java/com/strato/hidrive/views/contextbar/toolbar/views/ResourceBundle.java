package com.strato.hidrive.views.contextbar.toolbar.views;

import android.graphics.drawable.Drawable;

import com.strato.hidrive.views.contextbar.R;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * Created by Shevchuk Anton on 22.12.2015.
 */

public class ResourceBundle {
	@DrawableRes
	private int topItemSelector;
	@DrawableRes
	private int itemSelector;
	@DrawableRes
	private int bottomItemSelector;
	@DrawableRes
	private int topBottomItemSelector;
	@ColorRes
	private int fontColor = R.color.white;

	private Drawable dividerColor;
	private int menuWidth;
	private int menuPaddingTop;
	private int menuElevationPadding;

	public int getTopItemSelector() {
		return this.topItemSelector;
	}

	public void setTopItemSelector(int topItemSelector) {
		this.topItemSelector = topItemSelector;
	}

	public int getItemSelector() {
		return this.itemSelector;
	}

	public void setItemSelector(int itemSelector) {
		this.itemSelector = itemSelector;
	}

	public int getBottomItemSelector() {
		return this.bottomItemSelector;
	}

	public void setBottomItemSelector(int bottomItemSelector) {
		this.bottomItemSelector = bottomItemSelector;
	}

	public int getTopBottomItemSelector() {
		return this.topBottomItemSelector;
	}

	public void setTopBottomItemSelector(int topBottomItemSelector) {
		this.topBottomItemSelector = topBottomItemSelector;
	}

	public int getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public Drawable getDividerColor() {
		return this.dividerColor;
	}

	public void setDividerColor(Drawable dividerColor) {
		this.dividerColor = dividerColor;
	}

	public int getMenuWidth() {
		return menuWidth;
	}

	public void setMenuWidth(int menuWidth) {
		this.menuWidth = menuWidth;
	}

	public int getMenuPaddingTop() {
		return menuPaddingTop;
	}

	public void setMenuPaddingTop(int menuPaddingTop) {
		this.menuPaddingTop = menuPaddingTop;
	}

	public int getMenuElevationPadding() {
		return menuElevationPadding;
	}

	public void setMenuElevationPadding(int menuElevationPadding) {
		this.menuElevationPadding = menuElevationPadding;
	}
}

