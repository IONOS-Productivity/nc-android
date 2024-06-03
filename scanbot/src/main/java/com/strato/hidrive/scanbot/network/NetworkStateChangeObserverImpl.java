package com.strato.hidrive.scanbot.network;

import android.content.Context;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by: Alex Kucherenko
 * Date: 17.05.2017.
 */

public class NetworkStateChangeObserverImpl implements NetworkStateChangeObserver {
	private static final int PERIOD_FOR_STATE_UPDATE = 1;
	private final BehaviorSubject<NetworkStateBundle> stateObservable;

	public NetworkStateChangeObserverImpl(Context context) {
		NetworkStateBundle defaultStateBundle = NetworkUtils.getNetworkStateBundle(context);
		this.stateObservable = BehaviorSubject.createDefault(defaultStateBundle);
		NetworkStateChangeReceiver.registerListener((listenerContext, intent, state) ->
			this.stateObservable.onNext(state)
		);
	}

	@Override
	public Observable<NetworkStateBundle> stateObservable() {
		return this.stateObservable.sample(PERIOD_FOR_STATE_UPDATE, TimeUnit.SECONDS);
	}
}
