package com.strato.hidrive.views.contextbar.di

import android.content.Context
import com.strato.hidrive.views.contextbar.ContextActionBar

interface ContextbarComponent {

	companion object{
		fun from(from: Context?): ContextbarComponent {
			return (from?.applicationContext as? ContextbarComponentProvider)
				?.getComponent()
				?: throw IllegalStateException("No available context")
		}
	}

	fun inject(exifInfoView: ContextActionBar)

}