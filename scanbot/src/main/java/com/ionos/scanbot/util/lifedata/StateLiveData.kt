/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.util.lifedata

import androidx.lifecycle.LiveData

internal abstract class StateLiveData<S : Any>(initialValue: S) : LiveData<S>(initialValue) {

	override fun getValue(): S {
		return super.getValue() ?: throw IllegalStateException()
	}

	operator fun invoke(): S = getValue()
}
