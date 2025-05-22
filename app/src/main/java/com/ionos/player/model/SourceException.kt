package com.ionos.player.model

class SourceException(
    errorCode: Int = 0,
) : Exception(
    "Source not found. Error code: $errorCode",
)
