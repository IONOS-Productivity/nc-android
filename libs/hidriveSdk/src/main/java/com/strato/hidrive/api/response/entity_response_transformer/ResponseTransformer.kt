package com.strato.hidrive.api.response.entity_response_transformer

/**
 * User: Dima Muravyov
 * Date: 26.02.2018
 */
@Suppress("AddVarianceModifier")
fun interface ResponseTransformer<Response, Entity> {

	/**
	 * Transforms response object to entity
	 */
	fun transform(response: Response): Entity
}