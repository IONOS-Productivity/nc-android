package com.ionos.scanbot.upload.target_provider

class UploadTargetRepositoryImpl(initialVue: UploadTarget) : UploadTargetRepository {
	override var uploadTarget: UploadTarget = initialVue
}
