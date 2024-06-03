package com.ionos.domain.manager.interfaces;

import com.ionos.domain.entity.RemoteFileInfo;
import com.ionos.domain.optional.Optional;

import org.jetbrains.annotations.Nullable;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by dmy on 17.06.2015.
 */
public interface ICachedRemoteFileMgr {

    Completable delete(String path);

    Single<Optional<RemoteFileInfo>> getFileFromCache(String path);

    @Nullable
    RemoteFileInfo blockingFindParentFileInfo(RemoteFileInfo file);

    Single<Boolean> isItemInCache(String path);

    Single<Boolean> dirContainsChildWithName(String dirPath, String fileName);
}