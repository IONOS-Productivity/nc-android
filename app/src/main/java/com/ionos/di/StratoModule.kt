package com.ionos.di

import com.ionos.privacy.DataProtectionActivity
import com.ionos.scanbot.di.NCScanbotModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [NCScanbotModule::class])
abstract class StratoModule {

    @ContributesAndroidInjector
    abstract fun dataProtectionActivity(): DataProtectionActivity
}
