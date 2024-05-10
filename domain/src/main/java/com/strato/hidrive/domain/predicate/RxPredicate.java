package com.strato.hidrive.domain.predicate;


import io.reactivex.Single;

/**
 * Created by Anton Shevchuk on 12.08.2016.
 */

public interface RxPredicate<T> {
	Single<Boolean> satisfied(T value);
}
