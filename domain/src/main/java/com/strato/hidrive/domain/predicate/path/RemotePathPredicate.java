package com.strato.hidrive.domain.predicate.path;

import com.strato.hidrive.domain.provider.HidrivePathProvider;

import javax.inject.Inject;

/**
 * Created by Sergey Shandyuk on 9/7/2016.
 */
public class RemotePathPredicate implements PathPredicate {
	private final HidrivePathProvider pathProvider;

	@Inject
	RemotePathPredicate(HidrivePathProvider pathProvider) {
		this.pathProvider = pathProvider;
	}

	@Override
	public boolean satisfied(String path) {
		return path.startsWith(this.pathProvider.getCurrentUserFolderPath());
	}
}
