package com.strato.hidrive.domain.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.strato.hidrive.domain.entity.FavoritesUpdateEvent;
import com.strato.hidrive.domain.entity.RemoteFileInfo;
import com.strato.hidrive.domain.utils.file_view_display_params.SortType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by dmy on 18.06.2015.
 */
public interface FavoritesFileInfoRepository {

	boolean isInFavorites(RemoteFileInfo currentFile);

	Single<Boolean> isInFavoritesSingle(RemoteFileInfo currentFile);

	int isInFavorites(List<RemoteFileInfo> files);

	void toggleFavorite(@NonNull RemoteFileInfo file, boolean add);

	Single<Boolean> isEmpty();

	Single<List<RemoteFileInfo>> getAllAsFiles();

	Single<List<RemoteFileInfo>> getAllAsFiles(String userId);

	Single<List<RemoteFileInfo>> getInRange(int offset, int limit, SortType sortType);

	void removeFromFavorites(String path);

	Observable<FavoritesUpdateEvent> observeUpdateEvents();

	void toggleFavorites(
			@NonNull List<RemoteFileInfo> files,
			boolean add
	);

	@Nullable
    RemoteFileInfo update(
			@NonNull String path,
			@NonNull RemoteFileInfo newInfo
	);

	@NonNull
	Completable deleteThanInsertThanUpdate(
			@NonNull List<RemoteFileInfo> filesToDelete,
			@NonNull List<RemoteFileInfo> filesToInsert,
			@NonNull List<RemoteFileInfo> filesToUpdate
	);

	@NonNull
	Scheduler getFavoritesScheduler();
}
