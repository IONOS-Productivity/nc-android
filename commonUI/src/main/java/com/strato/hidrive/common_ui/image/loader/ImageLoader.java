package com.strato.hidrive.common_ui.image.loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.io.File;

public interface ImageLoader {

	ImageRequestBuilder load(@DrawableRes int drawableRes);

	ImageRequestBuilder load(@NonNull File file);

	ImageRequestBuilder load(@NonNull Bitmap bitmap);

	ImageRequestBuilder load(@NonNull Uri uri);

	ImageRequestBuilder load(@NonNull Drawable drawable);

	ImageRequestBuilder load(@NonNull Object model);

	ImageLoaderCache getCache();

}
