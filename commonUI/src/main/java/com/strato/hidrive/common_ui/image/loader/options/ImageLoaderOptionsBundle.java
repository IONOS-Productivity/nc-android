package com.strato.hidrive.common_ui.image.loader.options;

import androidx.annotation.Nullable;

import com.strato.hidrive.domain.optional.Optional;

public class ImageLoaderOptionsBundle implements ImageLoaderOptions {
	private final ScaleType scaleType;
	private final Optional<Size> size;
	private final Optional<RoundedCorners> roundedCorners;

	public ImageLoaderOptionsBundle() {
		this(ScaleType.CENTER_CROP, null, null);
	}

	public ImageLoaderOptionsBundle(ScaleType scaleType) {
		this(scaleType, null, null);
	}

	public ImageLoaderOptionsBundle(ScaleType scaleType, @Nullable Size size) {
		this(scaleType, size, null);
	}

	public ImageLoaderOptionsBundle(ScaleType scaleType, @Nullable Size size, @Nullable RoundedCorners roundedCorners) {
		this.scaleType = scaleType;
		this.size = Optional.fromNullable(size);
		this.roundedCorners = Optional.fromNullable(roundedCorners);
	}


	@Override
	public ScaleType getScaleType() {
		return this.scaleType;
	}

	@Override
	public Optional<Size> getSize() {
		return this.size;
	}

	@Override
	public Optional<RoundedCorners> getRoundedCorners() {
		return this.roundedCorners;
	}
}