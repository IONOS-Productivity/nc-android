package com.strato.hidrive.domain.repository;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import java.util.List;

/**
 * Created by Sergey Shandyuk on 4/6/2018.
 */
public interface SearchResultDataStore {
	List<RemoteFileInfo> getAll();

	void save(List<RemoteFileInfo> items);

	void clear();
}
