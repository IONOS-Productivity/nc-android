/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.util.widget

import androidx.viewpager.widget.ViewPager

internal fun ViewPager.addOnPageSelectedListener(listener: (position: Int) -> Unit) {
	addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
		override fun onPageSelected(position: Int) {
			listener.invoke(position)
		}
	})
}
