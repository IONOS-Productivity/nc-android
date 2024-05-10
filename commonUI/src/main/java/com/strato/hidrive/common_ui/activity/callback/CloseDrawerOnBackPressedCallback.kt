package com.strato.hidrive.common_ui.activity.callback

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener

class CloseDrawerOnBackPressedCallback(
	private val drawerLayout: DrawerLayout,
) : OnBackPressedCallback(false),
	DrawerListener {

	init {
		drawerLayout.addDrawerListener(this)
	}

	override fun handleOnBackPressed() {
		drawerLayout.closeDrawers()
	}

	override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
	}

	override fun onDrawerOpened(drawerView: View) {
		isEnabled = true
	}

	override fun onDrawerClosed(drawerView: View) {
		isEnabled = false
	}

	override fun onDrawerStateChanged(newState: Int) {
	}
}
