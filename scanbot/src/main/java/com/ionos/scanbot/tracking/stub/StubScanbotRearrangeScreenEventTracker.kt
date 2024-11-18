package com.ionos.scanbot.tracking.stub

import com.ionos.scanbot.tracking.ScanbotRearrangeScreenEventTracker
import javax.inject.Inject

class StubScanbotRearrangeScreenEventTracker @Inject constructor(
): ScanbotRearrangeScreenEventTracker {

    override fun trackBackPressed() = Unit

    override fun trackPictureDragged() = Unit

    override fun trackPage() = Unit

}