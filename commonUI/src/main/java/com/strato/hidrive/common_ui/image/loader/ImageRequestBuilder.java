package com.strato.hidrive.common_ui.image.loader;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.strato.hidrive.common_ui.image.loader.options.ImageLoaderOptions;
import com.strato.hidrive.common_ui.image.loader.target.BitmapTarget;
import com.strato.hidrive.common_ui.image.loader.target.DrawableTarget;
import com.strato.hidrive.domain.interfaces.actions.Action;

import java.util.concurrent.Future;

public interface ImageRequestBuilder {

	ImageRequestBuilder errorResources(@DrawableRes int errorRes);

	ImageRequestBuilder placeholder(@DrawableRes int placeholderRes);

	ImageRequestBuilder options(@NonNull ImageLoaderOptions options);

	ImageRequestBuilder onSuccess(@NonNull Action onSuccess);

	ImageRequestBuilder onError(@NonNull Action onError);

	void into(@NonNull BitmapTarget target);

	void into(@NonNull DrawableTarget target);

	Future<Bitmap> submit(Context context, int width, int height);
}
