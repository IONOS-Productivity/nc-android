package com.strato.hidrive.scanbot.di

import android.content.Context
import com.ionos.domain.exception.LogTryCatchExceptionHandler
import com.ionos.domain.exception.TryCatchExceptionHandler
import com.ionos.domain.utils.availability.Availability
import com.strato.hidrive.scanbot.BuildConfig
import com.strato.hidrive.scanbot.controller.ScanbotController
import com.strato.hidrive.scanbot.controller.ScanbotControllerImpl
import com.strato.hidrive.scanbot.di.qualifiers.Scanbot
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKey
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKeyUrl
import com.strato.hidrive.scanbot.initializer.ScanbotInitializer
import com.strato.hidrive.scanbot.initializer.ScanbotInitializerImpl
import com.strato.hidrive.scanbot.license.KeyStore
import com.strato.hidrive.scanbot.license.PreferencesKeyStore
import com.strato.hidrive.scanbot.license.oath.security.algorithm.AesGcmEncryptionAlgorithm
import com.strato.hidrive.scanbot.license.oath.security.algorithm.AlgorithmParameterSpecFactoryImpl
import com.strato.hidrive.scanbot.license.oath.security.key.AesGcmKeyRepository
import com.strato.hidrive.scanbot.license.oath.settings.SecureEncryptor
import com.strato.hidrive.scanbot.network.NetworkStateChangeObserver
import com.strato.hidrive.scanbot.network.NetworkStateChangeObserverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ScanbotModule {

    companion object {
        @Singleton
        @Provides
        fun provideSecureEncryptor(context: Context): SecureEncryptor {
            return SecureEncryptor(
                AesGcmEncryptionAlgorithm(
                    AesGcmKeyRepository(context).getKey(),
                    AlgorithmParameterSpecFactoryImpl()
                )
            )
        }

        @Provides
        fun provideNetworkStateChangeObserver(context: Context): NetworkStateChangeObserver {
            return NetworkStateChangeObserverImpl(context)
        }

        @Provides
        @ScanbotLicenseKey
        fun provideScanbotLicenseKey(): String {
            return BuildConfig.SCANBOT_LICENSE_KEY
        }

        @Provides
        @ScanbotLicenseKeyUrl
        fun provideScanbotLicenseKeyUrl(): String {
            return ""//BuildConfig.SCANBOT_LICENSE_KEY_URL
        }

        @Provides
        @Scanbot
        fun provideScanbotFeatureAvailability() = Availability {
            true //BuildConfig.IS_SCANBOT_FEATURE_AVAILABLE
        }

        @Singleton
        @Provides
        fun provideTryCatchExceptionHandler(): TryCatchExceptionHandler {
            // return if (BuildConfig.DEBUG
            // ) FatalTryCatchExceptionHandler()
            // else
            return  LogTryCatchExceptionHandler()
        }
    }

    @Binds
    abstract fun bindKeyStore(keyStore: PreferencesKeyStore): KeyStore

    @Binds
    abstract fun bindInitializer(initializer: ScanbotInitializerImpl): ScanbotInitializer

    @Binds
    abstract fun bindScanbotController(controller: ScanbotControllerImpl): ScanbotController

}