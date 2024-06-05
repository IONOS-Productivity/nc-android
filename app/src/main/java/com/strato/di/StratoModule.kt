package com.strato.di

import com.ionos.scanbot.license.LoadScanbotLicense
import com.strato.scanbot.LoadScanbotLicenseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class StratoModule {

    @Binds
    abstract fun bindLoadLicense(loadScanbotLicense: LoadScanbotLicenseImpl): LoadScanbotLicense

}