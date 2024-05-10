package com.strato.hidrive.common_ui.image.loader;

public interface ImageLoaderCache {
	void clearDiskMemory();

	void clearRAMemory();

	void onTrimMemory(int level);
}
