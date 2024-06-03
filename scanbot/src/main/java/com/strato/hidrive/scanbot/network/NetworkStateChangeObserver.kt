package com.strato.hidrive.scanbot.network

import io.reactivex.Observable

interface NetworkStateChangeObserver {
	fun stateObservable(): Observable<NetworkStateBundle>
}
