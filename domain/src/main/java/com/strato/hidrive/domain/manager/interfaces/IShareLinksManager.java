package com.strato.hidrive.domain.manager.interfaces;

import com.strato.hidrive.domain.entity.RemoteFileInfo;
import com.strato.hidrive.domain.entity.ShareLinkEntity;
import com.strato.hidrive.domain.manager.sharelink.SharedFileBundle;
import com.strato.hidrive.domain.optional.Optional;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmy on 17.06.2015.
 */
public interface IShareLinksManager {

	Completable clear();

	Observable<List<ShareLinkEntity>> loadShareLinks();

	Single<SharedFileBundle> getOrCreateShareLink(final RemoteFileInfo file);

	Single<Optional<ShareLinkEntity>> getShareLink(RemoteFileInfo file);

	Single<Optional<ShareLinkEntity>> getShareLink(String shareLinkId);

	Single<List<RemoteFileInfo>> getFilesForValidShareLinks();

	Single<List<ShareLinkEntity>> getValidShareLinks();

	Single<ShareLinkEntity> updateShareLink(ShareLinkEntity shareLink);

	boolean isLinkCorrect(ShareLinkEntity shareLink);

	String getLink(ShareLinkEntity shareLink);
}
