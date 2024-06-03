package com.ionos.domain.predicate.network

import com.ionos.domain.network.exception.NoWifiConnectionException
import com.ionos.domain.predicate.Predicate

/**
 * Created by: Alex Kucherenko
 * Date: 03.12.2018.
 */
class NoWiFiConnectionPredicate :
	Predicate<Throwable> {
	override fun satisfied(throwable: Throwable): Boolean = throwable is NoWifiConnectionException
}