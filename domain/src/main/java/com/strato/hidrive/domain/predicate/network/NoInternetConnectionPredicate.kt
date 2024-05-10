package com.strato.hidrive.domain.predicate.network

import com.strato.hidrive.domain.network.exception.NoInternetConnectionException
import com.strato.hidrive.domain.predicate.Predicate

/**
 * Created by: Alex Kucherenko
 * Date: 03.12.2018.
 */
class NoInternetConnectionPredicate :
	Predicate<Throwable> {
	override fun satisfied(throwable: Throwable): Boolean = throwable is NoInternetConnectionException
}