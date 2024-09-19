package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.strato.hidrive.views.contextbar.R;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.contextbar.utils.ParamAction;
import com.strato.hidrive.views.contextbar.utils.TintUtils;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * Created by zuzik on 09.12.2015.
 */
class ImageToolbarItemView extends ToolbarItemView {

	ImageToolbarItemView(Context context, ToolbarItem item) {
		super(context, item);
		inflate(context, R.layout.view_toolbar_item, this);
		ImageView imageView = findViewById(R.id.imageView);
		Drawable drawable = ResourcesCompat.getDrawable(
				getResources(),
				item.getToolbarImageResId(),
				null
		);
		if (item.getToolbarImageTintColorResId().isPresent()) {
			drawable = TintUtils.applyTint(drawable,
					getResources().getColor(item.getToolbarImageTintColorResId().get()),
					PorterDuff.Mode.MULTIPLY);
		} else {
			drawable = TintUtils.removeTint(drawable);
		}
		imageView.setImageDrawable(drawable);
		if (!item.isActive()) {
			imageView.setAlpha(0.21f);
			this.setEnabled(false);
		} else {
			imageView.setAlpha(1f);
			this.setEnabled(true);
		}
	}

	@Override
	public void setOnToolbarItemViewClickListener(@Nullable final ParamAction<ToolbarItem> action) {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (action != null) {
					action.execute(getItem());
				}
			}
		});
	}
}
