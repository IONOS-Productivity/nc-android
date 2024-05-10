package com.strato.hidrive.common_ui.activity

import androidx.appcompat.app.AppCompatDelegate
import com.phrase.android.sdk.Phrase

abstract class BasePhraseActivity : BaseSpyableActivity() {

	override fun getDelegate(): AppCompatDelegate {
		return Phrase.getDelegate(this, super.getDelegate())
	}

}