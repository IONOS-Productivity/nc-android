package com.strato.hidrive.domain.interfaces.actions

import io.reactivex.Single

/**
 * Created by Anton Shevchuk on 05.07.2016.
 */
interface Transformation<From, To> {
	fun transform(from: From): To
}

interface TransformationRx<From, To> : Transformation<From, Single<out To>>
