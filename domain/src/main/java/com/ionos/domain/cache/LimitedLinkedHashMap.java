package com.ionos.domain.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Sustains fixed size by removing oldest accessed value.
 * On each accessed value, this value will be moved to head(will be last to evict)
 *
 * @param <Key>
 * @param <Data>
 */
public class LimitedLinkedHashMap<Key, Data> extends LinkedHashMap<Key, Data> {

	public static final int INITIAL_CAPACITY = 16;
	private final int sizeThreshold;

	public LimitedLinkedHashMap(int sizeThreshold) {
		super(INITIAL_CAPACITY, 0.75f, true);
		this.sizeThreshold = sizeThreshold;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<Key, Data> eldest) {
		return size() > sizeThreshold;
	}

}
