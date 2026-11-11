/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.authorization_method

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.blikoon.qrcodescanner.QrCodeActivity
import com.nextcloud.client.device.DeviceInfo
import com.owncloud.android.R
import com.owncloud.android.authentication.AuthenticatorActivity
import com.owncloud.android.authentication.DeepLinkLoginActivity
import com.owncloud.android.databinding.ViewAuthorizationMethodBinding
import com.owncloud.android.utils.DisplayUtils
import com.owncloud.android.utils.PermissionUtil

class AuthorizationMethodActivity : AppCompatActivity() {

    companion object {
        private const val QR_SCAN_RESULT_KEY = "com.blikoon.qrcodescanner.got_qr_scan_relult"
        private const val LOGIN_URL_HOST = "login/"

        @JvmStatic
        fun createInstance(context: Context) = Intent(context, AuthorizationMethodActivity::class.java)
    }

    private val viewBinding by lazy { ViewAuthorizationMethodBinding.inflate(layoutInflater) }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission(), ::onCameraPermissionResult)

    private val qrScanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onQrScanResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        applySystemBarInsets()
        initListeners()
        initQrLogin()
    }

    private fun applySystemBarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.tvOr) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.updatePadding(bottom = insets.bottom)
            windowInsets
        }
    }

    private fun initListeners() {
        viewBinding.bLogin.setOnClickListener { login() }
    }

    private fun initQrLogin() {
        if (!DeviceInfo().hasCamera(this)) {
            viewBinding.bLoginQr.visibility = View.GONE
            return
        }

        viewBinding.bLoginQr.setOnClickListener { scanQrCode() }
    }

    private fun login() {
        val intent = Intent(this, AuthenticatorActivity::class.java)
        startActivity(intent)
    }

    private fun scanQrCode() {
        if (PermissionUtil.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            startQrScanner()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun onCameraPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            startQrScanner()
            return
        }

        DisplayUtils.showSnackMessageWithSettingsAction(
            viewBinding.root,
            getString(R.string.ionos_need_camera_permissions),
            this
        )
    }

    private fun startQrScanner() {
        qrScanLauncher.launch(Intent(this, QrCodeActivity::class.java))
    }

    private fun onQrScanResult(result: ActivityResult) {
        if (result.resultCode != RESULT_OK) {
            return
        }

        val scannedText = result.data?.getStringExtra(QR_SCAN_RESULT_KEY)
        if (scannedText == null || !isCompleteLoginQrCode(scannedText)) {
            DisplayUtils.showSnackMessage(this, R.string.qr_could_not_be_read)
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, scannedText.toUri(), this, DeepLinkLoginActivity::class.java)
        startActivity(intent)
    }

    private fun isCompleteLoginQrCode(scannedText: String): Boolean {
        val prefix = loginUrlPrefix()
        val loginUrlInfo = try {
            if (scannedText.startsWith(prefix)) AuthenticatorActivity.parseLoginDataUrl(prefix, scannedText) else null
        } catch (_: IllegalArgumentException) {
            null
        }

        return loginUrlInfo != null &&
            loginUrlInfo.server.isNotBlank() &&
            loginUrlInfo.loginName.isNotBlank() &&
            loginUrlInfo.appPassword.isNotBlank()
    }

    private fun loginUrlPrefix(): String =
        getString(R.string.login_data_own_scheme) + AuthenticatorActivity.PROTOCOL_SUFFIX + LOGIN_URL_HOST
}
