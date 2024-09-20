package com.ionos.exif_info.di

import com.ionos.exif_info.metadata_factory.GetExifMetadataGatewayFactory
import com.ionos.exif_info.metadata_factory.GetExifMetadataGatewayFactoryImpl
import com.ionos.exif_info.tracking.ExifInfoEventTrackerImpl
import com.ionos.exif_info.transformation.BytesToStringSizeTransformationImpl
import com.ionos.exif_info.transformation.MillisToDateDescriptionTransformationImpl
import com.ionos.exif_info.transformation.MillisToTimeDescriptionTransformationImpl
import com.ionos.exif_info.transformation.PathToTitleTransformationImpl
import com.ionos.player.exif_info.ExifInfoShowErrorImpl
import com.strato.hidrive.views.exif_info.ExifInfoShowError
import com.strato.hidrive.views.exif_info.ExifInfoViewDependencies
import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker
import com.strato.hidrive.views.exif_info.transformation.BytesToStringSizeTransformation
import com.strato.hidrive.views.exif_info.transformation.MillisToDateDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.MillisToTimeDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.PathToTitleTransformation
import dagger.Binds
import dagger.Module

@Module
abstract class NCExifInfoModule {

	@Binds
	abstract fun bindPathToTitleTitleTransformation(
		factory: PathToTitleTransformationImpl
	): PathToTitleTransformation

	@Binds
	abstract fun bindBytesToStringSizeTransformation(
		transformation: BytesToStringSizeTransformationImpl
	): BytesToStringSizeTransformation

	@Binds
	abstract fun bindMillisToTimeDescriptionTransformation(
		transformation: MillisToTimeDescriptionTransformationImpl
	): MillisToTimeDescriptionTransformation

	@Binds
	abstract fun bindMillisToDateDescriptionTransformation(
		transformation: MillisToDateDescriptionTransformationImpl
	): MillisToDateDescriptionTransformation

	@Binds
	abstract fun bindExifInfoEventTracker(
		tracker: ExifInfoEventTrackerImpl
	): ExifInfoEventTracker

	@Binds
	abstract fun bindExifInfoShowError(
		showError: ExifInfoShowErrorImpl
	): ExifInfoShowError


	@Binds
	abstract fun bindGetExifMetadataGatewayFactory(
		factory: GetExifMetadataGatewayFactoryImpl
	): GetExifMetadataGatewayFactory

	@Binds
	abstract fun bindExifInfoViewDependencies(
		impl: ExifInfoViewDependenciesImpl
	): ExifInfoViewDependencies
}