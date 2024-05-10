package com.strato.hidrive.domain.predicate.file_info.filter

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.predicate.file_info.FileInfoPredicate
import com.strato.hidrive.domain.predicate.file_info.encryption.EncryptionSystemFilePredicate
import javax.inject.Inject

class FilterEncryptedSystemFilePredicate @Inject constructor(
	private val encryptedSystemFilePredicate: EncryptionSystemFilePredicate,
): FileInfoPredicate {

	override fun satisfied(value: RemoteFileInfo): Boolean {
		return !encryptedSystemFilePredicate.satisfied(value)
	}

}