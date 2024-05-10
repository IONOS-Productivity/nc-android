package com.strato.hidrive.api.connection.apiclient

import com.strato.hidrive.api.connection.certificate.CertificateManager
import com.strato.hidrive.api.connection.converter.ConverterFactory
import com.strato.hidrive.api.connection.cookie.CookiesJar
import com.strato.hidrive.api.connection.cookie.CookiesRepository
import com.strato.hidrive.api.connection.gateway.exceptions.MissingAccessTokenProviderException
import com.strato.hidrive.api.connection.gateway.interfaces.AccessTokenProvider
import com.strato.hidrive.api.connection.retrofit.*
import com.strato.hidrive.api.connection.retry.RetryPolicyConfiguration
import com.strato.hidrive.api.oauth.OAuthRefreshTokenManager
import com.strato.hidrive.api.oauth.refresh_token.Token
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

/**
 * Created by: Alex Kucherenko
 * Date: 21.02.2018.
 */

class ApiClientWrapperBuilder(private val baseUrl: String) {

	companion object {
		private const val MAX_REQUESTS_PER_HOST = 5
		private const val CONNECT_TIMEOUT = 20L
	}

	enum class LoggingLevel(val level: HttpLoggingInterceptor.Level) {
		NONE(HttpLoggingInterceptor.Level.NONE),
		BASIC(HttpLoggingInterceptor.Level.BASIC),
		HEADERS(HttpLoggingInterceptor.Level.HEADERS),
		BODY(HttpLoggingInterceptor.Level.BODY)
	}


	private var loggingLevel: LoggingLevel = LoggingLevel.NONE
	private var readTimeoutInSeconds: Long? = null
	private var writeTimeoutInSeconds: Long? = null
	private var connectionTimeoutInSeconds: Long = CONNECT_TIMEOUT
	private var cookiesRepository: CookiesRepository? = null
	private var maxRequestsPerHost: Int = MAX_REQUESTS_PER_HOST
	private var accessTokenProvider: AccessTokenProvider? = null
	private var refreshTokenManager: OAuthRefreshTokenManager<out Token>? = null
	private var networkInterceptors: MutableList<Interceptor> = mutableListOf()
	private var interceptors: MutableList<Interceptor> = mutableListOf()
	private var userAgent: String? = null
	private var sslCertificate: X509Certificate? = null
	private var retryPolicyConfiguration: RetryPolicyConfiguration = RetryPolicyConfiguration.RetryPolicyBuilder().build()

	fun setLoggingLevel(loggingLevel: LoggingLevel): ApiClientWrapperBuilder = this.apply {
		this.loggingLevel = loggingLevel
	}

	fun setReadTimeoutInSeconds(readTimeoutInSeconds: Long): ApiClientWrapperBuilder = this.apply {
		this.readTimeoutInSeconds = readTimeoutInSeconds
	}

	fun setWriteTimeoutInSeconds(writeTimeoutInSeconds: Long): ApiClientWrapperBuilder = this.apply {
		this.writeTimeoutInSeconds = writeTimeoutInSeconds
	}

	fun setConnectionTimeoutInSeconds(connectionTimeoutInSeconds: Long): ApiClientWrapperBuilder = this.apply {
		this.connectionTimeoutInSeconds = connectionTimeoutInSeconds
	}

	fun setCookiesRepository(cookiesRepository: CookiesRepository): ApiClientWrapperBuilder = this.apply {
		this.cookiesRepository = cookiesRepository
	}

	fun setMaxRequestsPerHost(maxRequestsPerHost: Int): ApiClientWrapperBuilder = this.apply {
		this.maxRequestsPerHost = maxRequestsPerHost
	}

	fun setAccessTokenProvider(accessTokenProvider: AccessTokenProvider): ApiClientWrapperBuilder = this.apply {
		this.accessTokenProvider = accessTokenProvider
	}

	/**
	 * If you set @param refreshTokenManager you should also set AccessTokenProvider via setAccessTokenProvider
	 */
	fun setRefreshTokenManager(refreshTokenManager: OAuthRefreshTokenManager<out Token>): ApiClientWrapperBuilder = this.apply {
		this.refreshTokenManager = refreshTokenManager
	}

	fun addNetworkInterceptors(vararg networkInterceptors: Interceptor): ApiClientWrapperBuilder = this.apply {
		this.networkInterceptors.addAll(networkInterceptors)
	}

	fun addInterceptors(vararg interceptors: Interceptor): ApiClientWrapperBuilder = this.apply {
		this.interceptors.addAll(interceptors)
	}

	fun setUserAgent(userAgent: String): ApiClientWrapperBuilder = this.apply {
		this.userAgent = userAgent
	}

	fun setSslCertificate(certificate: X509Certificate): ApiClientWrapperBuilder = this.apply {
		this.sslCertificate = certificate;
	}

	fun setRetryPolicyConfiguration(retryPolicyConfiguration: RetryPolicyConfiguration): ApiClientWrapperBuilder = this.apply {
		this.retryPolicyConfiguration = retryPolicyConfiguration
	}

	/**
	 * @throws MissingAccessTokenProviderException if OAuthRefreshTokenManager is set and AccessTokenProvider isn't
	 */
	fun build(): ApiClientWrapper {
		return ApiClientWrapper(lazy(::buildRetrofit))
	}

	private fun buildOkHttpClient(): OkHttpClient {
		validateParams()

		val client = OkHttpClient.Builder()
				.apply {
					connectTimeout(connectionTimeoutInSeconds, TimeUnit.SECONDS)
					readTimeoutInSeconds?.let { readTimeout(it, TimeUnit.SECONDS) }
					writeTimeoutInSeconds?.let { writeTimeout(it, TimeUnit.SECONDS) }
					val httpLoggingInterceptor = HttpLoggingInterceptor()
					addNetworkInterceptor(httpLoggingInterceptor.apply { httpLoggingInterceptor.level = loggingLevel.level })
					cookiesRepository?.let { cookieJar(CookiesJar(it)) }
					accessTokenProvider?.let { addInterceptor(AuthInterceptor(it)) }
					refreshTokenManager?.let { authenticator(Authenticator(it)) }
					addInterceptor(ErrorInterceptor())
					retryPolicyConfiguration.errorsWhichNeedRetryWithDelay.onEach { errorCode ->
						addInterceptor(
								RetryWithDelayInterceptor(
										retryPolicyConfiguration.retryCount,
										retryPolicyConfiguration.delayInSeconds,
										errorCode))
					}
					retryPolicyConfiguration.errorsWhichNeedRetryWithoutDelay.onEach { errorCode ->
						addInterceptor(
								RetryInterceptor(
										retryPolicyConfiguration.retryCount,
										errorCode))
					}
					networkInterceptors.forEach { networkInterceptor -> addNetworkInterceptor(networkInterceptor) }
					interceptors.forEach { interceptor -> addInterceptor(interceptor) }
					userAgent?.let { addInterceptor(UserAgentInterceptor(it)) }
					sslCertificate?.let {
						val certManager = CertificateManager(it)
						sslSocketFactory(certManager.socketFactory, certManager.trustManager)
					}
				}
				.build()

		client.dispatcher.maxRequestsPerHost = maxRequestsPerHost

		return client
	}

	private fun buildRetrofit(): Retrofit {
		val client = buildOkHttpClient()
		return Retrofit.Builder()
				.baseUrl(baseUrl)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ConverterFactory())
				.client(client)
				.build()
	}

	private fun validateParams() {
		if (refreshTokenManager != null && accessTokenProvider == null) throw MissingAccessTokenProviderException()
	}
}