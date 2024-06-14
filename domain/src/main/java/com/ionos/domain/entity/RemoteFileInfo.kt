package com.ionos.domain.entity

import java.io.File
import java.io.Serializable
import java.util.*

/**
 * A entity that describes a file or folder stored remotely.
 *
 * Primary fields: [path], [id], [name], [isDirectory], [lastModified],
 * [creationTime], [contentLength], [isReadable], [isWritable],
 * [isTeamfolder], [membersCount], [mHash], [imageInfo]
 *
 * @property id unique file identifier
 * @property lastModified Get last modified date timestamp. Value in milliseconds
 * @property creationTime Value in milliseconds
 * @property isReadable Indicates that we have read permission for this file
 * @property isWritable Indicates that we have write permission for this file
 * @property isTeamfolder Indicates whether the directory is a teamfolder or not
 * @property membersCount Get total count of files in the directory independently from offsets and limits.
 * 						  Hidden files also will be counted. This value retrieved from the server.
 */
data class RemoteFileInfo @JvmOverloads constructor(
	val path: String = "",
	val id: String? = null,
	val name: String = path.substring(path.lastIndexOf(File.separator) + 1),
	val isDirectory: Boolean = false,
	val lastModified: Long = 0L,
	val creationTime: Long = 0L,
	val contentLength: Long = 0L,
	val isReadable: Boolean = true,
	val isWritable: Boolean = true,
	val isTeamfolder: Boolean = false,
	val membersCount: Int = 0,
	val mHash: String = "",
	//val imageInfo: ImageInfo? = null,
	private var decodedName: String? = null,
	// todo future refactoring: make key immutable
	var exportedDirectoryKey: Any? = null,
	// todo future refactoring: make key immutable
	@Transient
	var directoryKey: Any? = null,
	val parentId: String? = null,
) : Serializable, Comparable<RemoteFileInfo> {

	/**
	 * Check is decoded name defined - should return false, if getDecodedName() is same as getName()
	 *
	 * @return decoded name of file
	 */
	fun hasDecodedName() = decodedName != null

	/**
	 * Get decoded name of file. If name is not encoded should return just same as getName()
	 *
	 * @return decoded name of file
	 */
	fun getDecodedName() = decodedName ?: name

	/**
	 * Compares this file with another
	 *
	 * @return true if files equal (determined by comparison path, size and some other flags)
	 */
	override fun equals(other: Any?): Boolean {
		val file = if (other is RemoteFileInfo) other
		else return false

		return path == file.path
				&& isDirectory == file.isDirectory
				&& lastModified == file.lastModified
				&& (contentLength == file.contentLength || isDirectory)
	}

	override fun hashCode(): Int {
		var hash = 7
		hash = 31 * hash + name.hashCode()
		hash = 31 * hash + path.hashCode()
		return hash
	}

	/**
	 * Compares this object with the specified object for order.
	 */
	override fun compareTo(other: RemoteFileInfo): Int {
		return when{
			isDirectory && !other.isDirectory-> -1
			!isDirectory && other.isDirectory-> 1
			else -> {
				name.lowercase(Locale.getDefault())
					.compareTo(other.name.lowercase(Locale.getDefault()))
			}
		}
	}

	@Deprecated("Should not change data state.")
	fun setDecodedName(decodedName: String?) {
		this.decodedName = decodedName
	}
}