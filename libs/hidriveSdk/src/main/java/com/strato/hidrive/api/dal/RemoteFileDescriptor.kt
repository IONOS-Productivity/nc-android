// package com.strato.hidrive.api.dal
//
// sealed class RemoteFileDescriptor private constructor(
//     internal val pid: String?,
//     internal val path: String?,
// )
//
// data class PidDescriptor(val value: String) : RemoteFileDescriptor(pid = value, path = null)
//
// data class PathDescriptor(val value: String) : RemoteFileDescriptor(pid = null, path = value)
//
// data class CompositeDescriptor(
//     val dirPid: String,
//     val relativePath: String,
// ) : RemoteFileDescriptor(pid = dirPid, path = relativePath)
