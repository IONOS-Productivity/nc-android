package com.strato.hidrive.domain.upload.provider;

import java.util.List;

/**
 * Created by Sergey Shandyuk on 7/26/2016.
 */
public interface EntityUploadProvider {
	String getName();

	long getSize() throws CantGetSizeException;

	long getLastModified();

	boolean isDirectory();

	boolean exists();

	List<String> listFiles();
}
