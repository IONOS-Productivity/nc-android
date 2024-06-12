package com.ionos.di

import com.ionos.scanbot.license.LoadScanbotLicense
import com.ionos.scanbot.license.LoadScanbotLicenseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class StratoModule {

    @Binds
    abstract fun bindLoadLicense(loadScanbotLicense: LoadScanbotLicenseImpl): LoadScanbotLicense

}