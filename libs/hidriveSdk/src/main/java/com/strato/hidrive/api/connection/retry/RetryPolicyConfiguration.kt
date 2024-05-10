package com.strato.hidrive.api.connection.retry

import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode

class RetryPolicyConfiguration private constructor(val retryCount: Int,
												   val delayInSeconds: Int,
												   val errorsWhichNeedRetryWithDelay: List<ErrorCode>,
												   val errorsWhichNeedRetryWithoutDelay: List<ErrorCode>) {

	class RetryPolicyBuilder {
		private var retryCount = 0
		private var delayInSeconds = 0
		private var errorsWhichNeedRetryWithDelay = emptyList<ErrorCode>()
		private var errorsWhichNeedRetryWithoutDelay = emptyList<ErrorCode>()

		fun retryCount(retryCount: Int): RetryPolicyBuilder {
			this.retryCount = retryCount
			return this
		}

		fun delayInSeconds(delayInSeconds: Int): RetryPolicyBuilder {
			this.delayInSeconds = delayInSeconds
			return this
		}

		fun errorsWhichNeedRetryWithDelay(errorsWhichNeedRetryWithDelay: List<ErrorCode>): RetryPolicyBuilder {
			this.errorsWhichNeedRetryWithDelay = errorsWhichNeedRetryWithDelay
			return this
		}

		fun errorsWhichNeedRetryWithoutDelay(errorsWhichNeedRetryWithoutDelay: List<ErrorCode>): RetryPolicyBuilder {
			this.errorsWhichNeedRetryWithoutDelay = errorsWhichNeedRetryWithoutDelay
			return this
		}

		fun build(): RetryPolicyConfiguration = RetryPolicyConfiguration(
				this.retryCount,
				this.delayInSeconds,
				this.errorsWhichNeedRetryWithDelay,
				this.errorsWhichNeedRetryWithoutDelay)
	}
}
