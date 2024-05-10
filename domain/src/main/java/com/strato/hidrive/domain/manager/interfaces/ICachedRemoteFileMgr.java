package com.strato.hidrive.domain.manager.interfaces;

import com.strato.hidrive.api.bll.directory.get.Range;
import com.strato.hidrive.domain.entity.RemoteDir;
import com.strato.hidrive.domain.entity.RemoteFileInfo;
import com.strato.hidrive.domain.optional.Optional;
import com.strato.hidrive.domain.utils.file_view_display_params.SortType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmy on 17.06.2015.
 */
public interface ICachedRemoteFileMgr {

	void clearCache();

	Completable delete(String path);

	Single<RemoteDir> getDir(String path);

	Single<Optional<RemoteFileInfo>> getFileFromCache(String path);

	Single<Optional<RemoteDir>> getDirFromCache(String path);

	@NotNull
	Observable<RemoteFileInfo> observeFileInfo(@NotNull String path);

	@NotNull
	Observable<Long> observeDirChildrenCount(@NotNull String parentPath);

	@Nullable
	RemoteFileInfo blockingGetFileFromCache(String path);

	@Nullable
	RemoteDir blockingGetDirFromCache(String path);

	@Nullable
	RemoteDir blockingFindParentDirectory(RemoteFileInfo file);

	@Nullable
	RemoteFileInfo blockingFindParentFileInfo(RemoteFileInfo file);

	Single<Boolean> isItemInCache(String path);

	Single<RemoteDir> updateDir(String path, SortType sortType, Range range);

	Single<RemoteDir> updateDir(String path, SortType sortType);

	Single<RemoteDir> updateDir(String path);

	Single<RemoteDir> updateDir(RemoteFileInfo dirInfo, SortType sortType, Range range);

	Single<RemoteDir> updateDir(RemoteFileInfo dirInfo, SortType sortType);

	Single<RemoteDir> updateDir(RemoteFileInfo dirInfo);

	void insertFileInfo(RemoteFileInfo fileToInsert);

	void insertDir(RemoteDir dirToInsert);

	Single<Long> insertFileInfoSingle(RemoteFileInfo fileToInsert);

	Single<Long> insertDirSingle(RemoteDir dirToInsert);

	Single<Boolean> dirContainsChildWithName(String dirPath, String fileName);
}
