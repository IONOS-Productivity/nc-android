package com.strato.hidrive.api.connection.gateway.interfaces

import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import io.reactivex.Observable

/**
 * Created by: Alex Kucherenko
 * Date: 21.02.2018.
 */
interface Gateway<Entity> {

	/**
	 * Execute gateway request synchronously
	 */
	fun execute(): Observable<DomainGatewayResult<Entity>>

}