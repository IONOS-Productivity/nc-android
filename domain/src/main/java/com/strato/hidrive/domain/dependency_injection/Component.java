package com.strato.hidrive.domain.dependency_injection;

/**
 * Created by Anton Shevchuk on 22.08.2017.
 */

public interface Component {
	void init();

	void release();
}
