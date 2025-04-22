package com.ionos.player.transformation.oc_file

import com.ionos.player.NeighborFilesTypes
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.utils.MimeTypeUtil
import com.ionos.player.transformation.PlayerTransformation
import javax.inject.Inject

class OCFileToNeighborFilesTypesTransformation @Inject constructor(
): PlayerTransformation<OCFile, NeighborFilesTypes> {

    override fun transform(from: OCFile): NeighborFilesTypes {
        return when{
            MimeTypeUtil.isAudio(from) -> NeighborFilesTypes.AUDIO
            MimeTypeUtil.isVideo(from) -> NeighborFilesTypes.VIDEO
            else -> NeighborFilesTypes.FILES
        }
    }

}