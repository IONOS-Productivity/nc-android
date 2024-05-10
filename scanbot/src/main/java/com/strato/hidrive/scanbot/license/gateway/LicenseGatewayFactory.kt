// package com.strato.hidrive.scanbot.license.gateway
//
// import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
// import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
// import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicense
// import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKeyUrl
// import javax.inject.Inject
//
// /**
//  * User: Dima Muravyov
//  * Date: 29.01.2020
//  */
// internal class LicenseGatewayFactory @Inject constructor(
// 	@ScanbotLicense private val apiClientWrapper: ApiClientWrapper,
// 	@ScanbotLicenseKeyUrl private val licenseKeyUrl: String,
// ) {
//
// 	fun create(): Gateway<String> {
// 		return LicenseGateway(
// 			licenseKeyUrl,
// 			apiClientWrapper,
// 			LicenseResponseTransformer())
// 	}
// }
