package com.ionos.scanbot.exception

import java.io.IOException

internal class OpenFileException(path: String?) : IOException(path)
