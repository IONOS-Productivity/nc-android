package com.strato.hidrive.domain.filemanager.sorting.comparators;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import java.util.Comparator;

/**
 * User: Dima Muravyov
 * Date: 12.08.2016
 */
public class GalleryInfoDateComparator implements Comparator<RemoteFileInfo> {

	@Override
	public int compare(RemoteFileInfo lhs, RemoteFileInfo rhs) {
		return Long.compare(lhs.getLastModified(), rhs.getLastModified());
	}
}