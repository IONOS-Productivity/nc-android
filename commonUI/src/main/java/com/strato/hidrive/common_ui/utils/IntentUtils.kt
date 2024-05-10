package com.strato.hidrive.common_ui.utils

import android.content.Intent
import android.net.Uri
import java.util.*

/**
 * Created with love by Yehor Levchenko.
 * Date: 28.07.2022.
 */
class IntentUtils {

    fun getUrisFromIntent(data: Intent?): List<Uri> {
        var uris = mutableListOf<Uri>()

        data?.let { intent ->
            intent.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) uris.add(clipData.getItemAt(i).uri)
            } ?: intent.data?.let { uri ->
                uris = Collections.singletonList(uri)
            } ?: listOf<Uri>()
        }

        return uris
    }
}