package com.strato.hidrive.domain.utils.suspendable

interface Suspendable {
    suspend fun suspend()

    fun proceed()
}