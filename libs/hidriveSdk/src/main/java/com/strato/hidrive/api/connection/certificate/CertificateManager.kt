package com.strato.hidrive.api.connection.certificate

import java.security.KeyStore
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by: Alex Kucherenko
 * Date: 15.08.2019.
 */

class CertificateManager(certificate: X509Certificate) {

	val socketFactory: SSLSocketFactory
	val trustManager: X509TrustManager

	init {
		val keyStoreType = KeyStore.getDefaultType()
		val keyStore = KeyStore.getInstance(keyStoreType).apply {
			load(null, null)
			setCertificateEntry("ca", certificate)
		}
		val trustManagerFactory: TrustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm())
				.apply {
					init(keyStore)
				}
		trustManager = trustManagerFactory.trustManagers[0] as X509TrustManager
		socketFactory = SSLContext.getInstance("TLSv1.2").apply {
			init(null, trustManagerFactory.trustManagers, null)
		}.socketFactory
	}

}
