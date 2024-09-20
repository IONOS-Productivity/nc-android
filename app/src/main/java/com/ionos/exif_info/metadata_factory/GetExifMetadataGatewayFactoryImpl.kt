package com.ionos.exif_info.metadata_factory

import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.utils.MimeType
import com.strato.hidrive.views.exif_info.ExifMetaData
import io.reactivex.Single
import javax.inject.Inject

class GetExifMetadataGatewayFactoryImpl @Inject constructor(
) : GetExifMetadataGatewayFactory {

    override fun create(file: OCFile): Single<ExifMetaData> {
        return Single.just(
            with(file) {
                ExifMetaData(
                    remotePath,
                    modificationTimestamp,
                    mimeType == MimeType.DIRECTORY,
                    fileLength
                )
            }
        )
    }

}