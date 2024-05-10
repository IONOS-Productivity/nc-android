package com.strato.hidrive.domain.predicate.network

import com.strato.hidrive.domain.predicate.Predicate
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by: Alex Kucherenko
 * Date: 30.04.2018.
 */
class ConnectionProblemPredicate :
	Predicate<Throwable> {
	override fun satisfied(exception: Throwable): Boolean =
			exception is IOException && exception !is SocketTimeoutException
}