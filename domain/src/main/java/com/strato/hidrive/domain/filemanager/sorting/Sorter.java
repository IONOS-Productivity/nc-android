package com.strato.hidrive.domain.filemanager.sorting;

import com.strato.hidrive.domain.utils.file_view_display_params.GroupBy;
import com.strato.hidrive.domain.utils.file_view_display_params.SortType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alex Kucherenko on 14.06.2016.
 */
public abstract class Sorter<T> {
	public void sort(List<T> files, SortType sortType) {
		Collections.sort(files, getComparator(sortType));
	}

	public Comparator<T> getComparator(SortType sortType) {
		return getComparator(sortType, GroupBy.IS_DIRECTORY_DESC);
	}

	public abstract Comparator<T> getComparator(SortType sortType, GroupBy groupBy);
}
