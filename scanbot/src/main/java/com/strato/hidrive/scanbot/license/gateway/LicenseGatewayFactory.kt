package com.strato.hidrive.scanbot.license.gateway

import io.reactivex.Single

interface LicenseGatewayFactory{
	fun create(): Single<String>
}
