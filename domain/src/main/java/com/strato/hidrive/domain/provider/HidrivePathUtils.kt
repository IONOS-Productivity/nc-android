package com.strato.hidrive.domain.provider

/**
 * User: Dima Muravyov
 * Date: 05.10.2018
 */
interface HidrivePathUtils { 

	fun getParentPath(path: String): String?

	fun isRootFolder(path: String): Boolean
}