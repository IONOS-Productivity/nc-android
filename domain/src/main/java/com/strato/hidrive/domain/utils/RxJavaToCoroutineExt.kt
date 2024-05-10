package com.strato.hidrive.domain.utils

import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Single<T>.toSuspend(dispatcher: CoroutineDispatcher = Dispatchers.Unconfined): T = withContext(dispatcher) {
    suspendCancellableCoroutine { continuation: CancellableContinuation<T> ->
        val disposable = this@toSuspend.subscribe(
            { continuation.resume(it) },
            { continuation.resumeWithException(it) }
        )
        continuation.invokeOnCancellation { disposable.dispose() }
    }
}

suspend fun Completable.toSuspend(dispatcher: CoroutineDispatcher = Dispatchers.Unconfined) = withContext(dispatcher) {
    suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
        val disposable = this@toSuspend.subscribe(
            { continuation.resume(Unit) },
            { continuation.resumeWithException(it) }
        )
        continuation.invokeOnCancellation { disposable.dispose() }
    }
}