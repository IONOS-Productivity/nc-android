package com.ionos.exif_info.tracking

import android.content.Context
import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker
import javax.inject.Inject

class ExifInfoEventTrackerImpl @Inject constructor(
) : ExifInfoEventTracker {

    override fun trackCancel(context: Context) {
    }

}