package com.ionos.player.transformation

import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.PlayerFileInfoToExifInfoProviderTransformation
import com.strato.hidrive.views.exif_info.ExifInfoFile
import com.strato.hidrive.views.exif_info.ExifInfoProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PlayerFileInfoToExifInfoProviderTransformationImpl @Inject constructor(
    private val transformation: PlayerFileInfoToOCFileTransformation,
) : PlayerFileInfoToExifInfoProviderTransformation {
    override fun transform(from: PlayerFileInfo): ExifInfoProvider? {
        val ocFile = transformation.transform(from) ?: return null

        return ExifInfoProvider(
            ExifInfoFile(
                ocFile.fileName,
                ocFile.fileLength,
                ocFile.modificationTimestamp,
                0
            ),
            { Single.error(NotImplementedError("ExifInfoLoadMetadata not implemented")) },
            { _, _, _ -> Completable.error(NotImplementedError("Exif load image not implemented")) }
        )
    }
}