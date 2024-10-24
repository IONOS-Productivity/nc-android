package com.ionos.scanbot.exception

import android.net.Uri

internal class ImportPictureException(val pictureUri: Uri, cause: Throwable) : Exception(cause)
