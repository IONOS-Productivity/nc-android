package com.strato.hidrive.views.contextbar.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

public final class TintUtils {

	private TintUtils() {
	}

	public static Drawable applyTint(@NonNull Drawable drawable,
									 @ColorInt int tintColor,
									 @NonNull PorterDuff.Mode mode) {
		drawable = DrawableCompat.wrap(drawable);
		DrawableCompat.setTintMode(drawable, mode);
		DrawableCompat.setTint(drawable, tintColor);
		return drawable;
	}

	public static Drawable removeTint(@NonNull Drawable drawable) {
		drawable = DrawableCompat.wrap(drawable);
		DrawableCompat.setTintList(drawable, null);
		return drawable;
	}
}
