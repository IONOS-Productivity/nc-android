package com.ionos.scanbot.network

import io.reactivex.Observable

interface NetworkStateChangeObserver {
	fun stateObservable(): Observable<NetworkStateBundle>
}
