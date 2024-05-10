package com.strato.hidrive.domain.utils.file_view_display_params;

/**
 * Created by y.zozulia on 02.07.2015.
 */
public enum SortType {
	NAME_ASC(0),
	NAME_DESC(1),
	DATE_ASC(2),
	DATE_DESC(3),
	SIZE_ASC(4),
	SIZE_DESC(5),
	TYPE(6),
	SEARCH_SCREEN_TYPE(7);

	private final int id;

	SortType(int number) {
		this.id = number;
	}

	public int getNumber() {
		return this.id;
	}

	public static SortType findByKey(int id) {
		for (SortType st : SortType.values()) {
			if (id == st.id) return st;
		}
		throw new NullPointerException("Can't find sort type by id = " + id);
	}
}