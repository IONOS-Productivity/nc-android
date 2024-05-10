package com.strato.hidrive.domain.provider;

/**
 * User: Dima Muravyov
 * Date: 07.02.2017
 */

public interface PathProvider {

	String getCacheStorageFolderName();

	String getCacheRootFolderName();

	String getCacheFolderPath();

	String getThumbnailCacheFolderPath();

	String getTempMediaFolderPath();

	String getCacheRootFolderPath();

	String getCurrentUserFolderPath();

	String getPublicFolderPath();

	String getUsersFolderPath();
}
