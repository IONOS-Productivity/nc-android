package com.ionos.scanbot.ui

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ionos.scanbot.controller.ScanbotController
import com.owncloud.android.R
import com.owncloud.android.lib.common.utils.Log_OC
import com.owncloud.android.utils.DisplayUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ScanbotCameraPermissionHandler(
    private val scanbotController: ScanbotController,
    private val viewProvider: ViewProvider
) : DefaultLifecycleObserver {

    private var disposables: CompositeDisposable? = null

    companion object {
        private val TAG = ScanbotCameraPermissionHandler::class.java.simpleName
    }

    fun interface ViewProvider {
        fun getSnackbarView(): View?
    }

    override fun onCreate(owner: LifecycleOwner) {
        disposables = CompositeDisposable()
        subscribeToScanbotEvents()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposables?.clear()
        disposables = null
        owner.lifecycle.removeObserver(this)
    }

    private fun subscribeToScanbotEvents() {
        disposables?.add(
            scanbotController.cameraPermissionDenied
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { showCameraPermissionDeniedMessage() },
                    { error -> Log_OC.e(TAG, "Error in camera permission stream", error) }
                )
        )
    }

    private fun showCameraPermissionDeniedMessage() {
        val view = viewProvider.getSnackbarView() ?: return
        val context = view.context

        DisplayUtils.showSnackMessageWithSettingsAction(
            view,
            context.getString(R.string.ionos_need_camera_permissions),
            context
        )
    }
}