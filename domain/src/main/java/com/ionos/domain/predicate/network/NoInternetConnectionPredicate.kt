package com.ionos.domain.predicate.network

import com.ionos.domain.network.exception.NoInternetConnectionException
import com.ionos.domain.predicate.Predicate

/**
 * Created by: Alex Kucherenko
 * Date: 03.12.2018.
 */
class NoInternetConnectionPredicate :
    com.ionos.domain.predicate.Predicate<Throwable> {
	override fun satisfied(throwable: Throwable): Boolean = throwable is com.ionos.domain.network.exception.NoInternetConnectionException
}