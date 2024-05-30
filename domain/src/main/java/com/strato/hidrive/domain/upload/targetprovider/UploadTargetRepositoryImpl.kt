package com.strato.hidrive.domain.upload.targetprovider

class UploadTargetRepositoryImpl(initialVue: UploadTarget) : UploadTargetRepository {
	override var uploadTarget: UploadTarget = initialVue
}
