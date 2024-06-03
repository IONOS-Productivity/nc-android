package com.strato.scanbot

import com.strato.hidrive.scanbot.license.gateway.LicenseGatewayFactory
import io.reactivex.Single
import javax.inject.Inject

class LicenseGatewayFactoryImpl @Inject constructor(): LicenseGatewayFactory {

    override fun create(): Single<String> {
       return Single.create {
           it.onSuccess("")
       }
    }

}