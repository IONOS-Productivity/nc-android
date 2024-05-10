package com.strato.hidrive.domain.manager.interfaces;


import androidx.annotation.NonNull;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import java.io.File;

/**
 * Created by dmy on 18.06.2015.
 */
public interface CacheManager {

	void checkOrCreateNomediaFile(File forImage);

	boolean isStorageAvailable();

	boolean deleteTempDirectory();

	void deleteTempFile(String path);

	String getCorrespondingRemoteFilePath(File cachedFile);

	void clearAllFiles(@NonNull String userId);

	void clearAllThumbnails();

	void cleanCache(long additionalSize);

	void renameCacheFile(@NonNull RemoteFileInfo oldEntity, @NonNull RemoteFileInfo newEntity);

	void moveCacheFile(@NonNull RemoteFileInfo oldEntity, @NonNull RemoteFileInfo newEntity);

	File getCacheFile(@NonNull RemoteFileInfo entity);

	File getFileThumbnailFolder(@NonNull RemoteFileInfo entity);
}
