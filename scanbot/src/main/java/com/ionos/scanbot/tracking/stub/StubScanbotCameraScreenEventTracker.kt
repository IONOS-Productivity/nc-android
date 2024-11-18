package com.ionos.scanbot.tracking.stub

import com.ionos.scanbot.tracking.ScanbotCameraScreenEventTracker
import javax.inject.Inject

class StubScanbotCameraScreenEventTracker @Inject constructor(
) : ScanbotCameraScreenEventTracker {

    override fun trackCancelClicked() = Unit

    override fun trackBackPressed() = Unit

    override fun trackExitConfirmed() = Unit

    override fun trackExitDenied() = Unit

    override fun trackAutomaticCaptureToggled(enabled: Boolean) = Unit

    override fun trackFlashToggled(enabled: Boolean) = Unit

    override fun trackImportClicked() = Unit

    override fun trackImportCanceled() = Unit

    override fun trackTakePictureClicked() = Unit

    override fun trackPage() = Unit

}