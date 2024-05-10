package com.strato.hidrive.api.response.entity

/**
 * User: Dima Muravyov
 * Date: 05.03.2018
 */
data class RemoteFileInfoResponse(val name: String?,
								  val path: String?,
								  val id: String?,
								  val parent_id: String?,
								  val mtime: Long?,
								  val ctime: Long?,
								  val mhash: String?,
								  val type: String?,
								  val writable: Boolean?,
								  val readable: Boolean?,
								  val size: Long?,
								  val members: List<RemoteFileInfoResponse>?,
								  val rshare: List<RshareResponse>?,
								  val nmembers: Int?,
								  val teamfolder: Boolean?,
								  val image: ImageInfoResponse?,
								  val src: String?,
								  val src_id: String?)