package com.strato.hidrive.domain.predicate;

import com.strato.hidrive.domain.manager.interfaces.ICachedRemoteFileMgr;
import com.strato.hidrive.domain.predicate.RxPredicate;
import com.strato.hidrive.domain.utils.FileUtils;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Single;

/**
 * Created by Sergey Shandyuk on 9/14/2018.
 */
public class RemoteFilePathOverwritePredicate implements RxPredicate<String> {
	private final Provider<ICachedRemoteFileMgr> cachedRemoteFileMgrProvider;

	@Inject
	public RemoteFilePathOverwritePredicate(Provider<ICachedRemoteFileMgr> cachedRemoteFileMgrProvider) {
		this.cachedRemoteFileMgrProvider = cachedRemoteFileMgrProvider;
	}

	@Override
	public Single<Boolean> satisfied(String fullUploadPath) {
		String parentDirPath = FileUtils.getParentDirPath(fullUploadPath);
		String fileName = FileUtils.extractFileName(fullUploadPath);
		return getCachedRemoteFileMgr()
				.dirContainsChildWithName(parentDirPath, fileName)
				.onErrorReturn(__ -> false)
				.filter(Boolean::booleanValue)
				.switchIfEmpty(getCachedRemoteFileMgr()
						.isItemInCache(fullUploadPath));
	}

	private ICachedRemoteFileMgr getCachedRemoteFileMgr() {
		return this.cachedRemoteFileMgrProvider.get();
	}
}
