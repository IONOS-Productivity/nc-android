package com.ionos.scanbot.util.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}