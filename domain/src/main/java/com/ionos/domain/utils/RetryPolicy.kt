package com.ionos.domain.utils

private const val RETRY_PERIOD_MILLIS = 1_000L * 60 * 2
private const val DEFAULT_MAX_ATTEMPTS = 3

interface RetryPolicy {

	fun shouldRetry(attempts: Int, throwable: Throwable): Boolean

	companion object {
		val DEFAULT: RetryPolicy
			get() = FixedAttemptsNumber(maxAttempts = DEFAULT_MAX_ATTEMPTS)
	}

	class Never : RetryPolicy {
		override fun shouldRetry(attempts: Int, throwable: Throwable): Boolean = false
	}

	class FixedAttemptsNumber(private val maxAttempts: Int) : RetryPolicy {
		override fun shouldRetry(attempts: Int, throwable: Throwable): Boolean {
			return attempts != maxAttempts
		}
	}

	class FixedAttemptsPerPeriod(
		maxAttempts: Int = DEFAULT_MAX_ATTEMPTS,
		private val retryPeriod: Long = RETRY_PERIOD_MILLIS,
	) : RetryPolicy {
		private val fixedAttemptsNumber = FixedAttemptsNumber(maxAttempts)
		private val timeStampMillis = System.currentTimeMillis()

		override fun shouldRetry(attempts: Int, throwable: Throwable): Boolean {
			val invocationTime = System.currentTimeMillis()

			return (if (invocationTime > timeStampMillis + retryPeriod) false
			else fixedAttemptsNumber.shouldRetry(attempts, throwable))
		}
	}

}