package com.strato.di

import com.strato.scanbot.LicenseGatewayFactoryImpl
import com.ionos.scanbot.license.gateway.LicenseGatewayFactory
import dagger.Binds
import dagger.Module

@Module
abstract class StratoModule {

    @Binds
    abstract fun bindLicenseGatewayFactory(factory: LicenseGatewayFactoryImpl): LicenseGatewayFactory

}