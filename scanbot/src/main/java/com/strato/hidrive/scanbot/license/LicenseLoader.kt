// package com.strato.hidrive.scanbot.license
//
// import com.strato.hidrive.domain.logger.LoggerUtil
// import com.strato.hidrive.domain.optional.Optional
// import com.strato.hidrive.domain.network.NetworkStateChangeObserver
// import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKey
// import com.strato.hidrive.scanbot.license.gateway.LicenseGatewayFactory
// import io.reactivex.Observable
// import io.reactivex.Scheduler
// import io.reactivex.disposables.CompositeDisposable
// import javax.inject.Inject
// import javax.inject.Singleton
//
// /**
//  * User: Dima Muravyov
//  * Date: 29.01.2020
//  */
// @Singleton
// internal class LicenseLoader @Inject constructor(
// 	// private val licenseGatewayFactory: LicenseGatewayFactory,
// 	// private val networkStateChangeObserver: NetworkStateChangeObserver,
// 	private val licenseKeyStore: LicenseKeyStore,
// 	@ScanbotLicenseKey private val defaultLicenseKey: String,
// ) {
// 	private val compositeDisposable = CompositeDisposable()
//
// 	fun load(scheduler: Scheduler) {
// 		load(scheduler) {}
// 	}
//
// 	fun load(scheduler: Scheduler, onSuccess: (key: String) -> Unit) {
// 		stopAlreadyRunningLoading()
//
// 		compositeDisposable.add(networkStateChangeObserver
// 			.stateObservable()
// 			.filter { networkState -> networkState.online }
// 			.flatMap { licenseGatewayFactory.create().execute() }
// 			.flatMap { response ->
// 				if (response.result != null) {
// 					Observable.just(response.result)
// 				} else {
// 					Observable.error(response.error)
// 				}
// 			}
// 			.subscribeOn(scheduler)
// 			.subscribe(
// 				{ licenseKey ->
// 					licenseKeyStore.saveLicenseKey(licenseKey)
// 					onSuccess(licenseKey)
// 				},
// 				{ error -> LoggerUtil.logE(LicenseLoader::class.java.simpleName, error) }))
// 	}
//
// 	fun getLicenseKey(): String = licenseKeyStore
// 		.getLicenseKey()
// 		.or(defaultLicenseKey)
//
// 	fun getSavedLicenseKey(): Optional<String> {
// 		return licenseKeyStore.getLicenseKey()
// 	}
//
// 	private fun stopAlreadyRunningLoading() {
// 		if (compositeDisposable.size() > 0) {
// 			compositeDisposable.clear()
// 		}
// 	}
// }
