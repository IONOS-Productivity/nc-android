package com.strato.hidrive.domain.utils.kotlin.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by: Alex Kucherenko
 * Date: 28.09.2018.
 */
fun Disposable?.safeDispose() {
    if (this != null && !isDisposed) {
        dispose()
    }
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}