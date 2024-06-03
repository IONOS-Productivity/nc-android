package com.strato.hidrive.scanbot.screens.save

import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import com.ionos.common_ui.dialog.LockProgressDialog
import com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.OverwriteDialogs
import com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.upload.RemoteFilePathOverwriteDialogs
import com.ionos.domain.exception.NoFreeLocalSpaceException
import com.ionos.domain.utils.kotlin.extension.plusAssign
import com.strato.hidrive.scanbot.R
import com.strato.hidrive.scanbot.databinding.ScanbotActivitySaveBinding
import com.strato.hidrive.scanbot.di.inject
import com.strato.hidrive.scanbot.screens.base.BaseActivity
import com.strato.hidrive.scanbot.screens.save.SaveScreen.*
import com.strato.hidrive.scanbot.screens.save.SaveScreen.Event.*
import com.strato.hidrive.scanbot.exception.InvalidFileNameException
import com.strato.hidrive.scanbot.exception.OverwriteFilesException
import com.strato.hidrive.scanbot.exception.SaveDocumentException
import io.reactivex.disposables.CompositeDisposable

internal class SaveActivity : BaseActivity<Event, State, ViewModel>() {
	override val viewModelFactory: SaveViewModelFactory by inject { saveViewModelFactory() }
	override val viewBinding by lazy { ScanbotActivitySaveBinding.inflate(layoutInflater) }

    //TODO alk
	// private val uploadTargetPickerLauncher by inject { uploadTargetPickerLauncher() }
	private val exitDialog by inject { exitDialog() }
	private val progressDialog by lazy { LockProgressDialog() }

	// private val filterOverwriteFavoritesDialogFactory by inject { filterOverwriteFavoritesDialogFactory() }
	private val overwriteDialogsDisposable = CompositeDisposable()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initOnBackPressedCallback()
		initViews()
		// uploadTargetPickerLauncher.init(
		// 	this,
		// 	viewModel::onUploadTargetPicked,
		// 	viewModel::onUploadTargetPickerCanceled,
		// )
	}

	override fun onDestroy() {
		overwriteDialogsDisposable.clear()
		super.onDestroy()
	}

	private fun initOnBackPressedCallback() {
		onBackPressedDispatcher.addCallback(this, true) { viewModel.onBackPressed() }
	}

	private fun initViews() = with(viewBinding) {
		toolbar.tvTitle.text = getString(R.string.save_as)
		toolbar.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
		toolbar.tvSave.setOnClickListener { viewModel.onSaveClicked() }
		tvName.addTextChangedListener { viewModel.onFileNameChanged(it?.toString() ?: "") }
		fileTypeView.setOnFileTypeChangedListener { viewModel.onFileTypeChanged(it) }
		tvPath.setOnClickListener { viewModel.onSaveLocationPathClicked() }
	}

	override fun State.render() = with(viewBinding) {
		if (tvName.text.toString() != baseFileName) {
			tvName.setText(baseFileName)
		}
		fileTypeView.setCheckedFileType(fileType)
		tvPath.text = targetPath
		displayProgress(processing)
	}

	private fun displayProgress(display: Boolean) = if (display) {
		progressDialog.start(this, getString(R.string.scanbot_processing_document_title))
	} else {
		progressDialog.stop()
	}

	override fun Event.handle() = when (this) {
		is LaunchUploadTargetPickerEvent -> {}//uploadTargetPickerLauncher.launch(initialTarget, fileName)
		is HandleErrorEvent -> error.handle()
		is ShowExitDialogEvent -> showExitDialog()
		is CloseScreenEvent -> finish()
	}

	private fun Throwable.handle() = when (this) {
		is SaveDocumentException -> showSaveDocumentErrorMessage(cause)
		is InvalidFileNameException -> showMessage(R.string.incorrect_file_name)
		is OverwriteFilesException -> showOverwriteDialogs(overwritePaths)
		else -> showMessage(R.string.unknown_exception)
	}

	private fun showExitDialog() {
		exitDialog.show(context, viewModel::onExitConfirmed, viewModel::onExitDenied)
	}

	private fun showSaveDocumentErrorMessage(cause: Throwable?) = when (cause) {
		is NoFreeLocalSpaceException -> showMessage(R.string.no_free_space_message)
		else -> showMessage(R.string.scanbot_creating_document_error_message)
	}

	private fun showOverwriteDialogs(overwritePaths: List<String>) {
		overwriteDialogsDisposable.clear()
		overwriteDialogsDisposable += createOverwriteDialogs(overwritePaths)
			.filterOverwriteFiles()
			//.flatMap { createFilterFavoritesDialog(it).filter() }
			.subscribe { viewModel.onOverwriteDialogsResult(overwritePaths, it) }
	}

	private fun createOverwriteDialogs(overwritePaths: List<String>): OverwriteDialogs<String> {
		val onClose: (String) -> Unit = { overwriteDialogsDisposable.clear() }
		return RemoteFilePathOverwriteDialogs(overwritePaths, this, onClose)
	}

	// private fun createFilterFavoritesDialog(overwritePaths: List<String>): FilterFavoritesDialog<String> {
	// 	val destinationDirPath = FileUtils.getParentDirPath(overwritePaths[0])
	// 	val data = FilterFavoritesDialogData(destinationDirPath, overwritePaths, FileUtils::extractFileName)
	// 	return filterOverwriteFavoritesDialogFactory.create(data, context)
	// }
}
