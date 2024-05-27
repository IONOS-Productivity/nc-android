package com.nextcloud.client.di

// import com.strato.hidrive.BuildConfig
// import com.strato.hidrive.bll.upload.factory.UploadFactory
import com.owncloud.android.BuildConfig
import com.strato.hidrive.domain.exception.LogTryCatchExceptionHandler
import com.strato.hidrive.domain.exception.TryCatchExceptionHandler
import com.strato.hidrive.domain.utils.availability.Availability
import com.strato.hidrive.scanbot.controller.ScanbotController
import com.strato.hidrive.scanbot.controller.ScanbotControllerImpl
import com.strato.hidrive.scanbot.di.qualifiers.Scanbot
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicense
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKey
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKeyUrl
import com.strato.hidrive.scanbot.initializer.ScanbotInitializer
import com.strato.hidrive.scanbot.initializer.ScanbotInitializerImpl
// import com.strato.hidrive.scanbot.tracking.ScanbotCameraScreenEventTracker
// import com.strato.hidrive.scanbot.tracking.ScanbotCropScreenEventTracker
// import com.strato.hidrive.scanbot.tracking.ScanbotFilterScreenEventTracker
// import com.strato.hidrive.scanbot.tracking.ScanbotGalleryScreenEventTracker
// import com.strato.hidrive.scanbot.tracking.ScanbotRearrangeScreenEventTracker
// import com.strato.hidrive.scanbot.tracking.ScanbotSaveScreenEventTracker
// import com.strato.hidrive.scanbot.upload.UploadWithoutCheckForFileExisting
// import com.strato.hidrive.tracking.scanbot.ScanbotCameraScreenEventTrackerImpl
// import com.strato.hidrive.tracking.scanbot.ScanbotCropScreenEventTrackerImpl
// import com.strato.hidrive.tracking.scanbot.ScanbotFilterScreenEventTrackerImpl
// import com.strato.hidrive.tracking.scanbot.ScanbotGalleryScreenEventTrackerImpl
// import com.strato.hidrive.tracking.scanbot.ScanbotRearrangeScreenEventTrackerImpl
// import com.strato.hidrive.tracking.scanbot.ScanbotSaveScreenEventTrackerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ScanbotModule {

	@Provides
	@ScanbotLicenseKey
	fun provideScanbotLicenseKey(): String {
		return BuildConfig.SCANBOT_LICENSE_KEY
	}

	@Provides
	@ScanbotLicenseKeyUrl
	fun provideScanbotLicenseKeyUrl(): String {
		return ""//BuildConfig.SCANBOT_LICENSE_KEY_URL
	}

	@Provides
	@Scanbot
	fun provideScanbotFeatureAvailability() = Availability {
        true //BuildConfig.IS_SCANBOT_FEATURE_AVAILABLE
	}

	@Provides
	internal fun provideInitializer(initializer: ScanbotInitializerImpl): ScanbotInitializer {
		return initializer
	}

	@Provides
	fun provideScanbotController(controller: ScanbotControllerImpl): ScanbotController {
		return controller
	}

    @Singleton
    @Provides
    fun provideTryCatchExceptionHandler(): TryCatchExceptionHandler {
        // return if (BuildConfig.DEBUG
        // ) FatalTryCatchExceptionHandler()
        // else
          return  LogTryCatchExceptionHandler()
    }

	// @Provides
	// fun provideUploadWithoutCheckForFileExisting(uploadFactory: UploadFactory): UploadWithoutCheckForFileExisting {
	// 	return uploadFactory.create()
	// }

	// @Provides
	// fun provideCameraScreenEventTracker(eventTracker: ScanbotCameraScreenEventTrackerImpl): ScanbotCameraScreenEventTracker {
	// 	return eventTracker
	// }

	// @Provides
	// fun provideCropScreenEventTracker(eventTracker: ScanbotCropScreenEventTrackerImpl): ScanbotCropScreenEventTracker {
	// 	return eventTracker
	// }

	// @Provides
	// fun provideFilterScreenEventTracker(eventTracker: ScanbotFilterScreenEventTrackerImpl): ScanbotFilterScreenEventTracker {
	// 	return eventTracker
	// }

	// @Provides
	// fun provideGalleryScreenEventTracker(eventTracker: ScanbotGalleryScreenEventTrackerImpl): ScanbotGalleryScreenEventTracker {
	// 	return eventTracker
	// }
    //
	// @Provides
	// fun provideRearrangeScreenEventTracker(eventTracker: ScanbotRearrangeScreenEventTrackerImpl): ScanbotRearrangeScreenEventTracker {
	// 	return eventTracker
	// }
    //
	// @Provides
	// fun provideSaveScreenEventTracker(eventTracker: ScanbotSaveScreenEventTrackerImpl): ScanbotSaveScreenEventTracker {
	// 	return eventTracker
	// }
}
