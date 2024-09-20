package com.ionos.player.exif_info

import android.content.Context
import android.widget.Toast
import com.strato.hidrive.views.exif_info.ExifInfoShowError
import javax.inject.Inject

class ExifInfoShowErrorImpl @Inject constructor(
) : ExifInfoShowError {

	override fun invoke(context: Context, text: String?) {
		text?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
	}

}