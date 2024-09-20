package com.ionos.exif_info.metadata_factory

import com.owncloud.android.datamodel.OCFile
import com.strato.hidrive.views.exif_info.ExifMetaData
import io.reactivex.Single

interface GetExifMetadataGatewayFactory {
	fun create(file: OCFile): Single<ExifMetaData>
}