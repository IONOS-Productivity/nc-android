package com.strato.hidrive.scanbot.exception

import java.io.IOException

internal class OpenFileException(path: String?) : IOException(path)
