package com.ionos.scanbot.license

import com.ionos.logger.LoggerUtil
import com.ionos.domain.optional.Optional
import com.ionos.scanbot.di.qualifiers.ScanbotLicenseKey
import com.ionos.scanbot.license.gateway.LicenseGatewayFactory
import com.ionos.scanbot.network.NetworkStateChangeObserver
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * User: Dima Muravyov
 * Date: 29.01.2020
 */
@Singleton
internal class LicenseLoader @Inject constructor(
    private val licenseGatewayFactory: LicenseGatewayFactory,
    private val networkStateChangeObserver: NetworkStateChangeObserver,
    private val licenseKeyStore: LicenseKeyStore,
    @ScanbotLicenseKey private val defaultLicenseKey: String,
) {
    private val compositeDisposable = CompositeDisposable()

    fun load(scheduler: Scheduler) {
        load(scheduler) {}
    }

    fun load(scheduler: Scheduler, onSuccess: (key: String) -> Unit) {
        stopAlreadyRunningLoading()

        compositeDisposable.add(
            networkStateChangeObserver
                .stateObservable()
                .filter { networkState -> networkState.online }
                .flatMapSingle { licenseGatewayFactory.create() }
                .subscribeOn(scheduler)
                .subscribe(
                    { licenseKey ->
                        licenseKeyStore.saveLicenseKey(licenseKey)
                        onSuccess(licenseKey)
                    },
                    { error -> LoggerUtil.logE(LicenseLoader::class.java.simpleName, error) })
        )
    }

    fun getLicenseKey(): String = licenseKeyStore
        .getLicenseKey()
        .or(defaultLicenseKey)

    fun getSavedLicenseKey(): Optional<String> {
        return licenseKeyStore.getLicenseKey()
    }

    private fun stopAlreadyRunningLoading() {
        if (compositeDisposable.size() > 0) {
            compositeDisposable.clear()
        }
    }
}
