package com.strato.hidrive.domain.utils.availability

import io.reactivex.Observable

interface NetworkConnectivity {

	val status: Observable<ConnectionStatus>

	enum class ConnectionStatus { LOST, CONNECTED }
}