package com.strato.hidrive.scanbot.screens.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.ionos.common_ui.dialog.LockProgressDialog
import com.ionos.common_ui.utils.ContextUtils
import com.strato.hidrive.scanbot.R
import com.strato.hidrive.scanbot.databinding.ScanbotActivityFilterBinding
import com.strato.hidrive.scanbot.di.inject
import com.strato.hidrive.scanbot.filter.color.ColorFilterType
import com.strato.hidrive.scanbot.screens.base.BaseActivity
import com.strato.hidrive.scanbot.exception.CreateIntentException
import com.strato.hidrive.scanbot.screens.filter.FilterScreen.*
import com.strato.hidrive.scanbot.screens.filter.FilterScreen.Event.*
import com.strato.hidrive.scanbot.screens.filter.popup.FilterPopupWrapper
import com.strato.hidrive.scanbot.screens.filter.use_case.GetColorFilterName

internal class FilterActivity : BaseActivity<Event, State, ViewModel>() {

	companion object {
		private const val PICTURE_ID: String = "PICTURE_ID"
		private const val FILTER_TYPE: String = "FILTER_TYPE"

		fun createIntent(context: Context, pictureId: String, filterType: ColorFilterType): Intent {
			return Intent(context, FilterActivity::class.java).apply {
				putExtra(PICTURE_ID, pictureId)
				putExtra(FILTER_TYPE, filterType)
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
			}
		}
	}

	override val viewModelFactory by lazy { viewModelFactoryAssistant.create(getInitialState()) }
	override val viewBinding by lazy { ScanbotActivityFilterBinding.inflate(layoutInflater) }

	private val viewModelFactoryAssistant by inject { filterViewModelFactoryAssistant() }
	private val popup by lazy { FilterPopupWrapper(this, viewModel::onApplyForAllClicked) }
	private val progressDialog = LockProgressDialog()

	private val getColorFilterName by lazy { GetColorFilterName(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initOnBackPressedCallback()
		initListeners()
		viewBinding.toolbar.ivMore.visibility = View.VISIBLE
	}

	override fun onDestroy() {
		popup.dismiss()
		super.onDestroy()
	}

	private fun getInitialState(): State {
		val pictureId = intent?.getStringExtra(PICTURE_ID)
		val filterType = intent?.getSerializableExtra(FILTER_TYPE) as? ColorFilterType
		return if (pictureId != null && filterType != null) {
			State(pictureId, filterType)
		} else {
			throw CreateIntentException(this::class)
		}
	}

	private fun initOnBackPressedCallback() {
		onBackPressedDispatcher.addCallback(this, true) { viewModel.onBackPressed() }
	}

	private fun initListeners() = with(viewBinding) {
		filterControls.setListener(viewModel)
		toolbar.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
		toolbar.tvSave.setOnClickListener { viewModel.onSaveClicked() }
		toolbar.ivMore.setOnClickListener { viewModel.onMoreClicked() }
	}

	override fun State.render() = with(viewBinding) {
		toolbar.tvTitle.text = getColorFilterName(filterType)
		toolbar.ivMore.visibility = if (showMoreMenu) View.VISIBLE else View.GONE
		filterControls.setFilterType(filterType)
		imageView.setImageBitmap(image)
		renderProcessing(processing)
	}

	private fun renderProcessing(processing: Boolean) = if (processing) {
		progressDialog.start(this, getString(R.string.scanbot_filter_applying_for_all))
	} else {
		progressDialog.stop()
	}

	override fun Event.handle() = when (this) {
		is CloseScreenEvent -> finish()
		is ShowErrorEvent -> showMessage(R.string.fail)
		is ShowPopupEvent -> showPopup()
	}

	private fun showPopup() {
		popup.dismiss()
		if (!ContextUtils.isActivityFinishing(this)) {
			popup.showAsDropDown(viewBinding.toolbar.ivMore)
		}
	}
}