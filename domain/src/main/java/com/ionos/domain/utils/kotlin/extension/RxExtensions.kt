package com.ionos.domain.utils.kotlin.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by: Alex Kucherenko
 * Date: 28.09.2018.
 */

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}