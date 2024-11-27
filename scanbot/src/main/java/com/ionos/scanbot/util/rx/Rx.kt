/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.util.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}