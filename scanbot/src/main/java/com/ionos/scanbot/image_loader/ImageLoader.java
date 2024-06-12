
package com.ionos.scanbot.image_loader;

import java.io.File;

import androidx.annotation.NonNull;

public interface ImageLoader {

	ImageRequestBuilder load(@NonNull File file);

}
