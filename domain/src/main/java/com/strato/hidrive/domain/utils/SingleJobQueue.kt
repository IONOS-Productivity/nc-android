package com.strato.hidrive.domain.utils

import com.strato.hidrive.domain.logger.LoggerUtil
import io.reactivex.Completable
import io.reactivex.disposables.Disposables
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

abstract class SingleJobQueue<T> {

	protected val queue = ConcurrentLinkedQueue<T>()
	private var currentJob = Disposables.disposed()
	private val jobCompleted = AtomicBoolean(false)

	abstract fun addToQueue(data: T)

	abstract fun removeFromQueue(data: T)

	fun clearQueue() {
		queue.clear()
		jobCompleted.set(true)
		proceedNext()
	}

	@Synchronized
	protected fun proceedNext() {
		if (currentJob.isDisposed || jobCompleted.get()) {
			beforeQueuePoll()

			queue.poll()
				?.let {
					jobCompleted.set(false)
					currentJob = getJob(it)
						.doOnEvent { jobCompleted.set(true) }
						.subscribe(
							{ proceedNext() },
							{ throwable ->
								LoggerUtil.logE(this.javaClass.name, throwable)
								proceedNext()
							}
						)
				}
		}
	}

	protected abstract fun beforeQueuePoll()

	protected abstract fun getJob(data: T): Completable

}