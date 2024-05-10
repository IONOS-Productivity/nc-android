package com.strato.hidrive.api.dal

sealed interface RemoteFileListDescriptor

data class PidListDescriptor(val pids: List<String>) : RemoteFileListDescriptor

data class PathListDescriptor(val paths: List<String>) : RemoteFileListDescriptor

data class CompositeListDescriptor(
    val dirPid: String,
    val fileNames: List<String>,
) : RemoteFileListDescriptor
