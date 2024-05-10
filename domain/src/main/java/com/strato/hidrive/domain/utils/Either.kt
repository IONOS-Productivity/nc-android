package com.strato.hidrive.domain.utils

sealed class Either<Err, Data> {

	class Data<Data>(val data: Data) : Either<Nothing, Data>()

	class Error<Err>(val error: Err) : Either<Err, Nothing>()

	inline fun handleErrors(crossinline handler: (Err) -> Unit) {
		if (this is Error) handler.invoke(this.error)
	}

	inline fun handleData(crossinline handler: (Data) -> Unit) {
		if (this is Either.Data<Data>) handler.invoke(data)
	}

}