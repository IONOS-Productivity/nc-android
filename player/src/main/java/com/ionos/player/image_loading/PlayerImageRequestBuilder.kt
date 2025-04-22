package com.ionos.player.image_loading

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.ionos.player.util.Action
import java.util.concurrent.Future

interface PlayerImageRequestBuilder {

	fun onSuccess(onSuccess: Action): PlayerImageRequestBuilder

	fun onError(onError: Action): PlayerImageRequestBuilder

	fun errorResources(@DrawableRes errorRes: Int): PlayerImageRequestBuilder

	fun options(options: PlayerImageLoaderOptions): PlayerImageRequestBuilder

	fun submit(context: Context, width: Int, height: Int): Future<Bitmap>

	fun into(target: ImageView)

}