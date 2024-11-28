package com.ionos.di

import com.ionos.analycis.AnalyticsManager
import com.ionos.privacy.DataProtectionActivity
import com.ionos.analycis.FirebaseAnalyticsManager
import com.ionos.scanbot.di.NCScanbotModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [NCScanbotModule::class])
abstract class StratoModule {

    @ContributesAndroidInjector
    abstract fun dataProtectionActivity(): DataProtectionActivity

    @Binds
    abstract fun analyticsManager(firebaseAnalyticsManager: FirebaseAnalyticsManager): AnalyticsManager
}
