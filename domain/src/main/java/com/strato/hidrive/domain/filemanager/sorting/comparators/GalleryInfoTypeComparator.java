package com.strato.hidrive.domain.filemanager.sorting.comparators;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import java.util.Comparator;

/**
 * User: Dima Muravyov
 * Date: 12.08.2016
 */
public class GalleryInfoTypeComparator implements Comparator<RemoteFileInfo> {
	private static final char EXTENSION_SEPARATOR = '.';

	@Override
	public int compare(RemoteFileInfo lhs, RemoteFileInfo rhs) {
		String lhsExtension = getExtension(lhs);
		String rhsExtension = getExtension(rhs);

		if (!lhsExtension.isEmpty() && rhsExtension.isEmpty()) {
			return -1;
		} else if (lhsExtension.isEmpty() && !rhsExtension.isEmpty()) {
			return 1;
		} else {
			return lhsExtension.compareToIgnoreCase(rhsExtension);
		}
	}

	private String getExtension(RemoteFileInfo galleryInfo) {
		String name = getName(galleryInfo);
		int separatorIndex = name.lastIndexOf(EXTENSION_SEPARATOR);
		if (separatorIndex >= 0) {
			return name.substring(separatorIndex + 1);
		}
		return "";
	}

	private String getName(RemoteFileInfo galleryInfo) {
		return galleryInfo.getDecodedName();
	}
}