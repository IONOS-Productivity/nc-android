package com.ionos.scanbot.image_loader;

import android.widget.ImageView;

import androidx.annotation.NonNull;

public interface ImageRequestBuilder {

    ImageRequestBuilder options(@NonNull ImageLoaderOptions options);

    void into(@NonNull ImageView target);

}
