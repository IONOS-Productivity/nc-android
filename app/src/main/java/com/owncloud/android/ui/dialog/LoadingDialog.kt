/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2015 María Asensio Valverde <masensio@solidgear.es>
 * SPDX-FileCopyrightText: 2015 ownCloud Inc.
 * SPDX-License-Identifier: GPL-2.0-only AND (AGPL-3.0-or-later OR GPL-2.0-only)
 */
package com.owncloud.android.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ionos.annotation.IonosCustomization
import com.nextcloud.client.di.Injectable
import com.owncloud.android.R
import com.owncloud.android.databinding.LoadingDialogBinding
import com.owncloud.android.utils.theme.ViewThemeUtils
import javax.inject.Inject

class LoadingDialog :
    DialogFragment(),
    Injectable {

    @Inject
    lateinit var viewThemeUtils: ViewThemeUtils

    private var mMessage: String? = null
    private lateinit var binding: LoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        mMessage = arguments?.getString(ARG_MESSAGE)
    }

    @IonosCustomization
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LoadingDialogBinding.inflate(layoutInflater)
        binding.loadingText.text = mMessage
        return MaterialAlertDialogBuilder(requireContext(), R.style.Theme_ownCloud_LoadingDialog)
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        dialog?.setDismissMessage(null)
        super.onDestroyView()
    }

    companion object {
        private const val ARG_MESSAGE = "message"

        @JvmStatic
        fun newInstance(message: String?): LoadingDialog {
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }

            return LoadingDialog().apply {
                arguments = args
            }
        }
    }
}
