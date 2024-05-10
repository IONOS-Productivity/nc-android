package com.strato.hidrive.domain.manager.interfaces;

import com.strato.hidrive.domain.entity.Features;

public interface FeatureLoaderState {
	class Loaded implements FeatureLoaderState {
		public final Features features;

		public Loaded(Features features) {
			this.features = features;
		}
	}

	class Loading implements FeatureLoaderState {
	}

	class NotLoaded implements FeatureLoaderState {
	}

	class ErrorLoading implements FeatureLoaderState {
		public final Throwable error;

		public ErrorLoading(Throwable error) {
			this.error = error;
		}
	}
}