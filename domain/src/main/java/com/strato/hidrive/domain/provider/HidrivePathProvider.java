package com.strato.hidrive.domain.provider;

import java.io.File;

/**
 * User: Dima Muravyov
 * Date: 06.02.2017
 */

public interface HidrivePathProvider extends PathProvider {
	String SEPARATOR = File.separator;
	String CACHE_STORAGE_FOLDER = "HiDriveCacheStorage";
	String THUMBNAILS_DIRECTORY = "thumbnails";
	String ROOT_FOLDER_NAME = "root";
	String USERS_DIR_NAME = "users";
	String TEMP_MEDIA_FOLDER = "temp_media";
	String PUBLIC_DIR_NAME = "public";
	String PLAYER_CACHE_FOLDER_NAME = "player";
	String FILE_MANAGER_INTEGRATION_DIRECTORY = "file_manager_integration";

	String getCacheUserFolderName();

	String getCachePublicFolderName();

	String getCachePublicFolderPath();

	String getTeamfolderPath();

	String getCacheStorageFolderPath();

	String getPlayerCacheFolderName();

	String getFileManagerIntegrationPath();
}
