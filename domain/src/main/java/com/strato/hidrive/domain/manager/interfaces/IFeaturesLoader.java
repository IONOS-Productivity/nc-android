package com.strato.hidrive.domain.manager.interfaces;

import io.reactivex.Observable;

/**
 * Created by dmy on 17.06.2015.
 */
public interface IFeaturesLoader<T> {

	void refresh();

	void onDestroy();

	Observable<T> stateObservable();
}
