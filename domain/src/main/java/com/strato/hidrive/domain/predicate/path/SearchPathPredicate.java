package com.strato.hidrive.domain.predicate.path;

import com.strato.hidrive.domain.utils.FileUtils;

import javax.inject.Inject;

/**
 * Created by Sergey Shandyuk on 9/7/2016.
 */
public class SearchPathPredicate implements PathPredicate {

	@Inject
	public SearchPathPredicate() {
	}

	@Override
	public boolean satisfied(String path) {
		return path.equals(FileUtils.ROOT_PATH);
	}
}
