/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2023 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import androidx.core.graphics.drawable.DrawableCompat
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import com.ionos.annotation.IonosCustomization

object DrawableUtil {

    fun changeColor(source: Drawable, color: Int): Drawable {
        val drawable = DrawableCompat.wrap(source)
        DrawableCompat.setTint(drawable, color)
        return drawable
    }

    @IonosCustomization
    fun addDrawableAsOverlay(backgroundDrawable: Drawable, overlayDrawable: Drawable): LayerDrawable {

        return LayerDrawable(arrayOf(backgroundDrawable, overlayDrawable)).apply {
            setLayerSize(1, overlayDrawable.intrinsicWidth, overlayDrawable.intrinsicHeight)
            setLayerGravity(1, Gravity.CENTER)
        }
    }

    fun getResizedDrawable(context: Context, drawable: Drawable, pxSize: Int): Drawable {
        if (drawable is BitmapDrawable) {
            val originalBitmap = drawable.bitmap
            val scaledBitmap = originalBitmap.scale(pxSize, pxSize)
            return scaledBitmap.toDrawable(context.resources)
        }

        val bitmap = createBitmap(pxSize, pxSize)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, pxSize, pxSize)
        drawable.draw(canvas)

        return bitmap.toDrawable(context.resources)
    } 
}
