package com.strato.hidrive.common_ui.image.loader.options;

import com.strato.hidrive.domain.optional.Optional;

public interface ImageLoaderOptions {

	ScaleType getScaleType();

	Optional<Size> getSize();

	Optional<RoundedCorners> getRoundedCorners();
}
