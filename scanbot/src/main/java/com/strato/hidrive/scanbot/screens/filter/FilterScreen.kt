package com.strato.hidrive.scanbot.screens.filter

import android.graphics.Bitmap
import com.strato.hidrive.scanbot.filter.color.ColorFilterType
import com.strato.hidrive.scanbot.screens.base.BaseScreen
import com.strato.hidrive.scanbot.screens.filter.view.FilterControlsListener

internal interface FilterScreen {

	data class State(
		val pictureId: String,
		val filterType: ColorFilterType,
		val image: Bitmap? = null,
		val showMoreMenu: Boolean = false,
		val processing: Boolean = false,
		override val event: Event? = null,
	) : BaseScreen.State<Event>

	sealed interface Event : BaseScreen.Event {
		object CloseScreenEvent : Event
		data class ShowErrorEvent(val throwable: Throwable) : Event
		object ShowPopupEvent : Event
	}

	interface ViewModel : BaseScreen.ViewModel<Event, State>, FilterControlsListener {

		fun onBackPressed()

		fun onSaveClicked()

		fun onMoreClicked()

		fun onApplyForAllClicked()
	}
}