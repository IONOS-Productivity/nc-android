package com.strato.hidrive.domain.filemanager.sorting.comparators;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import java.util.Comparator;

/**
 * User: Dima Muravyov
 * Date: 12.08.2016
 */
public class GalleryInfoFileFolderComparator implements Comparator<RemoteFileInfo> {
	@Override
	public int compare(RemoteFileInfo lhs, RemoteFileInfo rhs) {
		if (lhs.isDirectory() && !rhs.isDirectory()) {
			return -1;
		} else if (!lhs.isDirectory() && rhs.isDirectory()) {
			return 1;
		}
		return 0;
	}
}
