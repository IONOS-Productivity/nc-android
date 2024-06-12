package com.ionos.di

import com.ionos.scanbot.di.ScanbotModule
import com.ionos.scanbot.image_loader.ImageLoader
import com.ionos.scanbot.image_loading.ImageLoaderImpl
import com.ionos.scanbot.license.LoadScanbotLicense
import com.ionos.scanbot.license.LoadScanbotLicenseImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ScanbotModule::class])
abstract class StratoModule {

    @Binds
    abstract fun bindLoadLicense(loadScanbotLicense: LoadScanbotLicenseImpl): LoadScanbotLicense

    @Binds
    abstract fun bindImageLoader(imageLoader: ImageLoaderImpl): ImageLoader
}