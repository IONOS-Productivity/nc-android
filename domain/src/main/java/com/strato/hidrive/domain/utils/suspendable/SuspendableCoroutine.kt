package com.strato.hidrive.domain.utils.suspendable

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class SuspendableCoroutine : Suspendable {
    private var continuation: CancellableContinuation<Unit>? = null

    override suspend fun suspend() = suspendCancellableCoroutine<Unit> {
        continuation = it
    }

    override fun proceed() {
        continuation?.resume(Unit)
        continuation = null
    }
}