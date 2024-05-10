package com.strato.hidrive.scanbot.di

import com.strato.hidrive.common_ui.dialog.stylized.overwrite_file_dialog.upload.FilterFavoritesDialogFactory
import com.strato.hidrive.common_ui.remote_target.UploadTargetPickerLauncher
import com.strato.hidrive.scanbot.controller.ScanbotController
import com.strato.hidrive.scanbot.screens.camera.CameraViewModelFactory
import com.strato.hidrive.scanbot.screens.crop.CropViewModelFactory
import com.strato.hidrive.scanbot.screens.filter.FilterViewModelFactory
import com.strato.hidrive.scanbot.screens.common.ExitDialog
import com.strato.hidrive.scanbot.screens.gallery.pager.GalleryPagerAdapter
import com.strato.hidrive.scanbot.screens.gallery.GalleryViewModelFactory
import com.strato.hidrive.scanbot.screens.rearrange.recycler.RearrangeAdapter
import com.strato.hidrive.scanbot.screens.rearrange.RearrangeViewModelFactory
import com.strato.hidrive.scanbot.screens.save.SaveViewModelFactory
import dagger.Subcomponent

@Subcomponent
abstract class ScanbotComponent {

	internal abstract fun cameraViewModelFactory(): CameraViewModelFactory

	internal abstract fun cropViewModelFactoryAssistant(): CropViewModelFactory.Assistant

	internal abstract fun filterViewModelFactoryAssistant(): FilterViewModelFactory.Assistant

	internal abstract fun galleryViewModelFactoryAssistant(): GalleryViewModelFactory.Assistant

	internal abstract fun galleryPagerAdapter(): GalleryPagerAdapter

	internal abstract fun rearrangeViewModelFactory(): RearrangeViewModelFactory

	internal abstract fun rearrangeAdapter(): RearrangeAdapter

	internal abstract fun saveViewModelFactory(): SaveViewModelFactory

	internal abstract fun exitDialog(): ExitDialog

	internal abstract fun scanbotController(): ScanbotController

    //TODO alk
	// internal abstract fun uploadTargetPickerLauncher(): UploadTargetPickerLauncher

	// internal abstract fun filterOverwriteFavoritesDialogFactory(): FilterFavoritesDialogFactory<String>
}
