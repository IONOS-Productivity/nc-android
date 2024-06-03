package com.ionos.scanbot.exception

internal class OverwriteFilesException(val overwritePaths: List<String>) : Exception()
